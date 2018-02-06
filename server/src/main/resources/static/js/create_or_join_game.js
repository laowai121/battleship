var FORM = {
    MAIN_MENU: 'mainMenu',
    CREATE_NEW_GAME: 'createNewGame',
    JOIN_EXISTING_GAME: 'joinExistingGame'
};

new Vue({
    el: '#battleship-app',
    data: {
        formDisplayed: FORM.MAIN_MENU,
        formLoading: false
    },
    methods: {
        createNewGameClicked: function () {
            this.formDisplayed = FORM.CREATE_NEW_GAME;
        },
        backToMenuClicked: function () {
            this.formDisplayed = FORM.MAIN_MENU;
        },
        joinExistingGameClicked: function () {
            this.formDisplayed = FORM.JOIN_EXISTING_GAME;
        },
        createAndJoinNewGame: function () {
            jQuery('input').attr('disabled', 'disabled');
            jQuery('button').attr('disabled', 'disabled');
            this.formLoading = true;
        }
    }
});