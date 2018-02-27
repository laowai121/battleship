var BATTLESHIP_PAGE = {
    MAIN_MENU: 'mainMenu',
    BATTLESHIP_GAME: 'battleshipGame'
};

var GAME_STATE = {
    // objective
    AWAITING_A: 'AWAITING_A',
    AWAITING_B: 'AWAITING_B',
    AWAITING_SHIPS: 'AWAITING_SHIPS',
    AWAITING_A_SHIPS: 'AWAITING_A_SHIPS',
    AWAITING_B_SHIPS: 'AWAITING_B_SHIPS',
    AWAITING_A_ATTACK: 'AWAITING_A_ATTACK',
    AWAITING_B_ATTACK: 'AWAITING_B_ATTACK',
    A_WON: 'A_WON',
    B_WON: 'B_WON',

    // subjective
    AWAITING_ME: 'AWAITING_ME',
    AWAITING_OPPONENT: 'AWAITING_OPPONENT',
    AWAITING_MY_SHIPS: 'AWAITING_MY_SHIPS',
    AWAITING_OPPONENT_SHIPS: 'AWAITING_OPPONENT_SHIPS',
    AWAITING_MY_ATTACK: 'AWAITING_MY_ATTACK',
    AWAITING_OPPONENT_ATTACK: 'AWAITING_OPPONENT_ATTACK',
    I_WON: 'I_WON',
    I_LOST: 'I_LOST',

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
        gameState: 'Loading...',
        players: {
            a: {
                name: ''
            },
            b: {
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

        this.openGame = function (playerToken, playerId, gameKey, playerName) {
            that.playerToken = playerToken;
            that.playerId = playerId;
            that.gameKey = gameKey;
            that.playerName = playerName;
            that.pageDisplayed = BATTLESHIP_PAGE.BATTLESHIP_GAME;

            battleshipLive.subscribe(playerToken);

            battleshipApi.getGameState(playerToken, function (gameState) {
                that.gameState = generateGameStateDescription(gameState);
            });

            battleshipApi.loadChatHistory(playerToken, function (history) {
                that.$refs.chat.load(history);
            });
        };

        this.generateGameStateDescription = function (gameState) {
            switch (gameState) {
                case GAME_STATE.AWAITING_A:

                case GAME_STATE.AWAITING_B:

                case GAME_STATE.AWAITING_SHIPS:

                case GAME_STATE.AWAITING_A_SHIPS:

                case GAME_STATE.AWAITING_B_SHIPS:

                case GAME_STATE.AWAITING_A_ATTACK:

                case GAME_STATE.AWAITING_B_ATTACK:

                case GAME_STATE.A_WON:

                case GAME_STATE.B_WON:

                case GAME_STATE.AWAITING_ME:

                case GAME_STATE.AWAITING_OPPONENT:

                case GAME_STATE.AWAITING_MY_SHIPS:

                case GAME_STATE.AWAITING_OPPONENT_SHIPS:

                case GAME_STATE.AWAITING_MY_ATTACK:

                case GAME_STATE.AWAITING_OPPONENT_ATTACK:

                case GAME_STATE.I_WON:

                case GAME_STATE.I_LOST:

                case GAME_STATE.UNKNOWN:

                default:
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
        playerJoined: function (playerName) {
            this.opponentPlayer = {
                name: playerName
            };
            this.showToast('Player <b>' + escapeHtml(playerName || 'Unknown') + '</b> joined the game');
        },
        spectatorJoined: function (playerName) {
            this.spectators.push({
                name: playerName
            });
            this.showToast('Spectator <b>' + escapeHtml(playerName || 'Unknown') + '</b> joined the game');
        }
    }
});