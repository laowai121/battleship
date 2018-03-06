var BATTLESHIP_PAGE = {
    MAIN_MENU: 'mainMenu',
    BATTLESHIP_GAME: 'battleshipGame'
};

var PLAYER_ROLE = {
    PLAYER_A: 'playerA',
    PLAYER_B: 'playerB',
    SPECTATOR: 'spectator'
};

var GAME_STATE = {
    AWAITING_A: 'AWAITING_A',
    AWAITING_B: 'AWAITING_B',
    AWAITING_SHIPS: 'AWAITING_SHIPS',
    AWAITING_A_SHIPS: 'AWAITING_A_SHIPS',
    AWAITING_B_SHIPS: 'AWAITING_B_SHIPS',
    AWAITING_A_ATTACK: 'AWAITING_A_ATTACK',
    AWAITING_B_ATTACK: 'AWAITING_B_ATTACK',
    A_WON: 'A_WON',
    B_WON: 'B_WON',
    LOADING: 'LOADING',
    UNKNOWN: 'UNKNOWN'
};

// from https://github.com/AStaroverov/vue-toast
var VueToast = window.vueToasts ? window.vueToasts.default || window.vueToasts : window.vueToasts;

var battleshipApp = new Vue({
    el: '#battleship-app',
    data: {
        pageDisplayed: BATTLESHIP_PAGE.MAIN_MENU,
        playerToken: '',
        gameKey: '',
        playerName: '',
        playerId: '',
        playerRole: PLAYER_ROLE.PLAYER_A,
        gameState: GAME_STATE.LOADING,
        players: {
            a: {
                id: '',
                name: ''
            },
            b: {
                id: '',
                name: ''
            }
        },
        spectators: []
    },
    components: {
        'vue-toast': VueToast,
        'main-menu': MainMenu,
        'battleship-chat': BattleshipChat
    },
    created: function () {
        var that = this;

        this.openGame = function (playerToken, playerId, gameKey,
                                  playerName, isSpectator, isPlayerA) {
            that.playerToken = playerToken;
            that.playerId = playerId;
            that.gameKey = gameKey;
            that.playerName = playerName;
            that.pageDisplayed = BATTLESHIP_PAGE.BATTLESHIP_GAME;

            if (isSpectator) {
                that.playerRole = PLAYER_ROLE.SPECTATOR;
            } else {
                that.playerRole = isPlayerA ? PLAYER_ROLE.PLAYER_A : PLAYER_ROLE.PLAYER_B;
            }

            battleshipLive.subscribe(playerToken);

            battleshipApi.getExtendedGameState(playerToken, function (extendedGameState) {
                var playerA = extendedGameState.playerA;
                var playerB = extendedGameState.playerB;

                if (playerA) {
                    that.players.a.id = playerA.id;
                    that.players.a.name = playerA.name;
                }

                if (playerB) {
                    that.players.b.id = playerB.id;
                    that.players.b.name = playerB.name;
                }

                extendedGameState.spectators.forEach(function (spectator) {
                    that.spectators.push(spectator);
                });

                that.gameState = extendedGameState.gameState;
            });

            battleshipApi.loadChatHistory(playerToken, function (history) {
                that.$refs.chat.load(history);
            });
        };

        this.generateGameStateDescription = function (gameState) {
            if (gameState === GAME_STATE.LOADING) {
                return 'Loading...';
            }

            var UNKNOWN_ERROR = 'Unknown Error. Unable to determine the game state';

            if (this.playerRole === PLAYER_ROLE.SPECTATOR) {
                switch (gameState) {
                    case GAME_STATE.AWAITING_A:
                        return 'Waiting for players to join the game';
                    case GAME_STATE.AWAITING_B:
                        return 'Waiting for the second player to join the game';
                    case GAME_STATE.AWAITING_SHIPS:
                        return 'Waiting for players to submit ships';
                    case GAME_STATE.AWAITING_A_SHIPS:
                        return 'Waiting for ' + this.players.a.name + ' to submit ships';
                    case GAME_STATE.AWAITING_B_SHIPS:
                        return 'Waiting for ' + this.players.b.name + ' to submit ships';
                    case GAME_STATE.AWAITING_A_ATTACK:
                        return this.players.a.name + '\'s turn';
                    case GAME_STATE.AWAITING_B_ATTACK:
                        return this.players.b.name + '\'s turn';
                    case GAME_STATE.A_WON:
                        return 'End of the game: ' + this.players.a.name + ' won';
                    case GAME_STATE.B_WON:
                        return 'End of the game: ' + this.players.b.name + ' won';
                    default:
                        return UNKNOWN_ERROR;
                }
            } else if (this.playerRole === PLAYER_ROLE.PLAYER_A || this.playerRole === PLAYER_ROLE.PLAYER_B) {
                var isPlayerA = this.playerRole === PLAYER_ROLE.PLAYER_A;

                switch (gameState) {
                    case GAME_STATE.AWAITING_A:
                        return isPlayerA ? UNKNOWN_ERROR : 'Waiting for opponent to join the game';
                    case GAME_STATE.AWAITING_B:
                        return isPlayerA ? 'Waiting for opponent to join the game' : UNKNOWN_ERROR;
                    case GAME_STATE.AWAITING_SHIPS:
                        return 'Submitting ships';
                    case GAME_STATE.AWAITING_A_SHIPS:
                        return isPlayerA ? 'Waiting for you to submit ships' : 'Waiting for opponent to submit ships';
                    case GAME_STATE.AWAITING_B_SHIPS:
                        return isPlayerA ? 'Waiting for opponent to submit ships' : 'Waiting for you to submit ships';
                    case GAME_STATE.AWAITING_A_ATTACK:
                        return isPlayerA ? 'Your turn to attack' : 'Waiting for opponent to attack';
                    case GAME_STATE.AWAITING_B_ATTACK:
                        return isPlayerA ? 'Waiting for opponent to attack' : 'Your turn to attack';
                    case GAME_STATE.A_WON:
                        return isPlayerA ? 'Congratulations! You have won!' : 'Sorry, you have lost';
                    case GAME_STATE.B_WON:
                        return isPlayerA ? 'Sorry, you have lost' : 'Congratulations! You have won!';
                    default:
                        return UNKNOWN_ERROR;
                }
            } else {
                return UNKNOWN_ERROR;
            }
        };

        this.showToast = function (message, theme) {
            theme = theme || 'success';
            that.$refs.toast.showToast(message, {
                theme: theme,
                timeLife: 5000,
                closeBtn: false
            });
        }
    },
    mounted: function () {
        this.$refs.toast.setOptions({
            maxToasts: 6,
            position: 'top right'
        });
    },
    methods: {
        copyGameKey: function () {
            if (copyTextToClipboard(this.gameKey)) {
                this.showToast('Game Key has been copied to clipboard');
            }
        },
        playerJoined: function (player) {
            var playerA = this.players.a;
            var playerB = this.players.b;
            var error = false;

            if (!playerA.id) {
                playerA.id = player.id;
                playerA.name = player.name;
            } else if (!playerB.id) {
                playerB.id = player.id;
                playerB.name = player.name;
            } else {
                error = true;
            }

            if (!error) {
                this.showToast('Player <b>' + escapeHtml(player.name || 'Unknown') + '</b> joined the game');
            }
        },
        spectatorJoined: function (spectator) {
            var error = false;

            this.spectators.forEach(function (s) {
                if (s.id === spectator.id) {
                    error = true;
                }
            });

            if (!error) {
                this.spectators.push(spectator);
                this.showToast('Spectator <b>' + escapeHtml(spectator.name || 'Unknown') + '</b> joined the game');
            }
        },
        reloadGameState: function () {
            var that = this;

            battleshipApi.getGameState(this.playerToken, function (gameState) {
                that.gameState = gameState;
            });
        }
    },
    computed: {
        opponentId: function () {
            if (this.playerRole !== PLAYER_ROLE.SPECTATOR) {
                return this.playerRole === PLAYER_ROLE.PLAYER_A ? this.players.b.id : this.players.a.id;
            } else {
                return '';
            }
        },
        opponentName: function () {
            if (this.playerRole !== PLAYER_ROLE.SPECTATOR) {
                return this.playerRole === PLAYER_ROLE.PLAYER_A ? this.players.b.name : this.players.a.name;
            } else {
                return '';
            }
        },
        gameStateDescription: function () {
            return this.generateGameStateDescription(this.gameState);
        }
    }
});