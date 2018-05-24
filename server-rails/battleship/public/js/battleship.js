var BattleshipPage = {
    MAIN_MENU: 'mainMenu',
    BATTLESHIP_GAME: 'battleshipGame'
};

// var PlayerRole = {
//     PLAYER_A: 'playerA',
//     PLAYER_B: 'playerB',
//     //SPECTATOR: 'spectator'
// };

// var GameState = {
//     AWAITING_A: 'AWAITING_A',
//     AWAITING_B: 'AWAITING_B',
//     AWAITING_SHIPS: 'AWAITING_SHIPS',
//     AWAITING_A_SHIPS: 'AWAITING_A_SHIPS',
//     AWAITING_B_SHIPS: 'AWAITING_B_SHIPS',
//     AWAITING_A_ATTACK: 'AWAITING_A_ATTACK',
//     AWAITING_B_ATTACK: 'AWAITING_B_ATTACK',
//     A_WON: 'A_WON',
//     B_WON: 'B_WON',
//     LOADING: 'LOADING',
//     UNKNOWN: 'UNKNOWN'
// };

// from https://github.com/AStaroverov/vue-toast
var VueToast = window.vueToasts ? window.vueToasts.default || window.vueToasts : window.vueToasts;

var battleshipApp = new Vue({
    el: '#battleship-app',
    data: {
        pageDisplayed: BattleshipPage.MAIN_MENU,
        // playerToken: '',
        playerKey: '',
        gameKey: '',
        // playerName: '',
        // myBattleField: null,
        // opponentBattleField: null,
        // playerId: '',
        // playerRole: PlayerRole.PLAYER_A,
        // gameState: GameState.LOADING,
        // players: {
        //     a: {
        //         id: '',
        //         name: '',
        //         battleField: null
        //     },
        //     b: {
        //         id: '',
        //         name: '',
        //         battleField: null
        //     }
        // },
        // spectators: [],
        // -----
        player: null,
        opponent: null
    },
    components: {
        'vue-toast': VueToast,
        'main-menu': MainMenu,
        'battleship-chat': BattleshipChat
    },
    created: function () {
        var that = this;

        // this.openGame = function (playerToken, playerId, gameKey,
        //                           playerName, isSpectator, isPlayerA) {
        //     that.playerToken = playerToken;
        //     that.playerId = playerId;
        //     that.gameKey = gameKey;
        //     that.playerName = playerName;
        //     that.pageDisplayed = BattleshipPage.BATTLESHIP_GAME;
        //
        //     if (isSpectator) {
        //         that.playerRole = PlayerRole.SPECTATOR;
        //     } else {
        //         that.playerRole = isPlayerA ? PlayerRole.PLAYER_A : PlayerRole.PLAYER_B;
        //     }
        //
        //     battleshipLive.subscribe(playerToken);
        //
        //     battleshipApi.getExtendedGameState(playerToken, function (extendedGameState) {
        //         var playerA = extendedGameState.playerA;
        //         var playerB = extendedGameState.playerB;
        //
        //         if (playerA) {
        //             that.players.a.id = playerA.id;
        //             that.players.a.name = playerA.name;
        //             that.players.a.battleField = extendedGameState.battleFieldA;
        //         }
        //
        //         if (playerB) {
        //             that.players.b.id = playerB.id;
        //             that.players.b.name = playerB.name;
        //             that.players.b.battleField = extendedGameState.battleFieldB;
        //         }
        //
        //         extendedGameState.spectators.forEach(function (spectator) {
        //             that.spectators.push(spectator);
        //         });
        //
        //         that.gameState = extendedGameState.gameState;
        //
        //
        //     });
        //
        //     battleshipApi.loadChatHistory(playerToken, function (history) {
        //         that.$refs.chat.populate(history);
        //     });
        // };
        //
        // this.generateGameStateDescription = function (gameState) {
        //     if (gameState === GameState.LOADING) {
        //         return 'Loading...';
        //     }
        //
        //     var UNKNOWN_ERROR = 'Unknown Error. Unable to determine the game state';
        //
        //     if (this.playerRole === PlayerRole.SPECTATOR) {
        //         switch (gameState) {
        //             case GameState.AWAITING_A:
        //                 return 'Waiting for players to join the game';
        //             case GameState.AWAITING_B:
        //                 return 'Waiting for the second player to join the game';
        //             case GameState.AWAITING_SHIPS:
        //                 return 'Waiting for players to submit ships';
        //             case GameState.AWAITING_A_SHIPS:
        //                 return 'Waiting for ' + this.players.a.name + ' to submit ships';
        //             case GameState.AWAITING_B_SHIPS:
        //                 return 'Waiting for ' + this.players.b.name + ' to submit ships';
        //             case GameState.AWAITING_A_ATTACK:
        //                 return this.players.a.name + '\'s turn';
        //             case GameState.AWAITING_B_ATTACK:
        //                 return this.players.b.name + '\'s turn';
        //             case GameState.A_WON:
        //                 return 'End of the game: ' + this.players.a.name + ' won';
        //             case GameState.B_WON:
        //                 return 'End of the game: ' + this.players.b.name + ' won';
        //             default:
        //                 return UNKNOWN_ERROR;
        //         }
        //     } else if (this.playerRole === PlayerRole.PLAYER_A || this.playerRole === PlayerRole.PLAYER_B) {
        //         var isPlayerA = this.playerRole === PlayerRole.PLAYER_A;
        //
        //         switch (gameState) {
        //             case GameState.AWAITING_A:
        //                 return isPlayerA ? UNKNOWN_ERROR : 'Waiting for opponent to join the game';
        //             case GameState.AWAITING_B:
        //                 return isPlayerA ? 'Waiting for opponent to join the game' : UNKNOWN_ERROR;
        //             case GameState.AWAITING_SHIPS:
        //                 return 'Submitting ships';
        //             case GameState.AWAITING_A_SHIPS:
        //                 return isPlayerA ? 'Waiting for you to submit ships' : 'Waiting for opponent to submit ships';
        //             case GameState.AWAITING_B_SHIPS:
        //                 return isPlayerA ? 'Waiting for opponent to submit ships' : 'Waiting for you to submit ships';
        //             case GameState.AWAITING_A_ATTACK:
        //                 return isPlayerA ? 'Your turn to attack' : 'Waiting for opponent to attack';
        //             case GameState.AWAITING_B_ATTACK:
        //                 return isPlayerA ? 'Waiting for opponent to attack' : 'Your turn to attack';
        //             case GameState.A_WON:
        //                 return isPlayerA ? 'Congratulations! You have won!' : 'Sorry, you have lost';
        //             case GameState.B_WON:
        //                 return isPlayerA ? 'Sorry, you have lost' : 'Congratulations! You have won!';
        //             default:
        //                 return UNKNOWN_ERROR;
        //         }
        //     } else {
        //         return UNKNOWN_ERROR;
        //     }
        // };

        this.openGame = function (playerKey) {
            battleshipApi.getGameState(playerKey, function (gameKey, player, opponent) {
                that.playerKey = playerKey;
                that.gameKey = gameKey;
                that.player = player;
                that.opponent = opponent;

                // battleshipLive.subscribe(playerToken);
                // battleshipChat.subscribe(playerToken);

                // battleshipChat.loadChatHistory(playerToken, function (history) {
                //     that.$refs.chat.populate(history);
                // });

                that.pageDisplayed = BattleshipPage.BATTLESHIP_GAME;
            }, function () {
                that.showToast('Unable to open the game', {
                    theme: 'error',
                    timeLife: 5000,
                    closeBtn: false
                });
            });
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
        // playerJoined: function (player) {
        //     var playerA = this.players.a;
        //     var playerB = this.players.b;
        //     var error = false;
        //
        //     if (!playerA.id) {
        //         playerA.id = player.id;
        //         playerA.name = player.name;
        //     } else if (!playerB.id) {
        //         playerB.id = player.id;
        //         playerB.name = player.name;
        //     } else {
        //         error = true;
        //     }
        //
        //     if (!error) {
        //         this.showToast('Player <b>' + escapeHtml(player.name || 'Unknown') + '</b> joined the game');
        //     }
        // },
        // spectatorJoined: function (spectator) {
        //     var error = false;
        //
        //     this.spectators.forEach(function (s) {
        //         if (s.id === spectator.id) {
        //             error = true;
        //         }
        //     });
        //
        //     if (!error) {
        //         this.spectators.push(spectator);
        //         this.showToast('Spectator <b>' + escapeHtml(spectator.name || 'Unknown') + '</b> joined the game');
        //     }
        // },
        // reloadGameState: function () {
        //     var that = this;
        //
        //     battleshipApi.getGameState(this.playerToken, function (gameState) {
        //         that.gameState = gameState;
        //     });
        // }
    },
    // computed: {
        // opponentId: function () {
        //     if (this.playerRole !== PlayerRole.SPECTATOR) {
        //         return this.playerRole === PlayerRole.PLAYER_A ? this.players.b.id : this.players.a.id;
        //     } else {
        //         return '';
        //     }
        // },
        // opponentName: function () {
        //     if (this.playerRole !== PlayerRole.SPECTATOR) {
        //         return this.playerRole === PlayerRole.PLAYER_A ? this.players.b.name : this.players.a.name;
        //     } else {
        //         return '';
        //     }
        // },
        // gameStateDescription: function () {
        //     return this.generateGameStateDescription(this.gameState);
        // }
    // }
});