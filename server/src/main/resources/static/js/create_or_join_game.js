new Vue({
    el: '#battleship-app',
    data: {
        createOrJoinGameFormShown: true,
        createNewGameFormShown: false
    },
    methods: {
        createNewGameClicked: function () {
            this.createOrJoinGameFormShown = false;
            this.createNewGameFormShown = true;
        },
        joinExistingGameClicked: function () {

        }
    }
});