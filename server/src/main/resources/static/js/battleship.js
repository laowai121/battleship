var BATTLESHIP_PAGE = {
    MAIN_MENU: 'mainMenu',
    BATTLESHIP_GAME: 'battleshipGame'
};

// from https://github.com/AStaroverov/vue-toast
var VueToast = window.vueToasts ? window.vueToasts.default || window.vueToasts : window.vueToasts;

var battleshipApp = new Vue({
    el: '#battleship-app',
    data: {
        pageDisplayed: BATTLESHIP_PAGE.MAIN_MENU,
        playerToken: '',
        gameKey: ''
    },
    components: {
        'vue-toast': VueToast,
        'main-menu': MainMenu,
        'battleship-chat': BattleshipChat
    },
    created: function () {
        var that = this;

        this.openGame = function (playerToken, gameKey) {
            that.playerToken = playerToken;
            that.gameKey = gameKey;
            that.pageDisplayed = BATTLESHIP_PAGE.BATTLESHIP_GAME;

            battleshipLive.subscribe(playerToken);

            battleshipApi.loadChatHistory(playerToken, function (history) {
                that.$refs.chat.load(history);
            });
        };

        this.showToast = function (message) {
            that.$refs.toast.showToast(message, {
                theme: 'success',
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
        }
    }
});