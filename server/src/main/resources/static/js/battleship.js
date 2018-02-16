var BATTLESHIP_PAGE = {
    MAIN_MENU: 'mainMenu',
    BATTLESHIP_GAME: 'battleshipGame'
};

var MENU_FORM = {
    MAIN: 'main',
    CREATE_GAME: 'createGame',
    JOIN_GAME: 'joinGame'
};

var MAX_SPECTATORS_UNLIMITED = -1;

var Validators = {
    playerNameValidator: {
        validate: function (playerName) {
            playerName = playerName.trim().replace(/\s\s+/g, ' ');

            var result = {
                normalized: playerName,
                valid: false,
                error: ''
            };

            if (playerName.length < 1) {
                result.error = 'Please, enter player name and try again';
            } else if (playerName.length > 20) {
                result.error = 'Player name is too long (maximum length: 20). Please, enter valid player name and try again'
            } else {
                result.valid = true;
            }

            return result;
        }
    },
    gameKeyValidator: {
        validate: function (gameKey) {
            gameKey = gameKey.trim().toLowerCase();

            var result = {
                normalized: gameKey,
                valid: false,
                error: ''
            };

            if (gameKey.length < 1) {
                result.error = 'Please, enter game key and try again';
            } else if (gameKey.length !== 16 || !/^[a-f0-9]+$/.test(gameKey)) {
                result.error = 'Invalid Game Key. Game Key should be a sequence of lower case alphanumerics of length 16'
            } else {
                result.valid = true;
            }

            return result;
        }
    }
};

var battleshipApp = new Vue({
    el: '#battleship-app',
    data: {
        pageDisplayed: BATTLESHIP_PAGE.MAIN_MENU,

        mainMenu: {
            loading: false,
            formDisplayed: MENU_FORM.MAIN,
            createGameForm: {
                playerName: {
                    value: '',
                    invalid: false,
                    validator: Validators.playerNameValidator
                },
                maxSpectators: {
                    value: MAX_SPECTATORS_UNLIMITED
                },
                validationError: ''
            },
            joinGameForm: {
                playerName: {
                    value: '',
                    invalid: false,
                    validator: Validators.playerNameValidator
                },
                gameKey: {
                    value: '',
                    invalid: false,
                    validator: Validators.gameKeyValidator
                },
                validationError: ''
            }
        }
    },
    created: function () {
        var that = this;

        this.mainMenuResetAllForms = function () {
            that.mainMenu.createGameForm.playerName.value = '';
            that.mainMenu.createGameForm.playerName.invalid = false;
            that.mainMenu.createGameForm.maxSpectators.value = MAX_SPECTATORS_UNLIMITED;
            that.mainMenu.joinGameForm.playerName.value = '';
            that.mainMenu.joinGameForm.playerName.invalid = false;
            that.mainMenu.joinGameForm.gameKey.value = '';
            that.mainMenu.joinGameForm.gameKey.invalid = false;
            that.mainMenu.createGameForm.validationError = '';
            that.mainMenu.joinGameForm.validationError = '';
        };

        this.validateForm = function (form) {
            var validationError = null;

            Object.getOwnPropertyNames(form).forEach(function (fieldName) {
                var validation;

                var field = form[fieldName];

                if (field.validator != null) {
                    validation = field.validator.validate(field.value);
                    field.value = validation.normalized;

                    if (validationError != null) {
                        field.invalid = false;
                    } else {
                        if (validation.valid) {
                            field.invalid = false;
                        } else {
                            field.invalid = true;
                            validationError = validation.error;
                        }
                    }
                }
            });

            form.validationError = validationError == null ? '' : validationError;

            return validationError == null;
        };

        this.createGame = function (asSpectator) {
            var formValid = that.validateForm(that.mainMenu.createGameForm);

            if (formValid) {
                that.mainMenu.loading = true;

                battleshipApi.createGame(
                    that.mainMenu.createGameForm.playerName.value,
                    that.mainMenu.createGameForm.maxSpectators.value,
                    asSpectator,
                    function (playerToken, gameKey) {
                        that.mainMenu.loading = false;
                        alert('Created');
                        // openGame(playerToken, gameKey);
                    },
                    function (errorMessage) {
                        that.mainMenu.loading = false;
                        that.mainMenu.createGameForm.validationError = errorMessage;
                    }
                );
            }
        };

        this.joinGame = function (asSpectator) {
            var formValid = that.validateForm(that.mainMenu.joinGameForm);

            if (formValid) {
                that.mainMenu.loading = true;

                battleshipApi.joinGame(
                    that.mainMenu.joinGameForm.playerName.value,
                    that.mainMenu.joinGameForm.gameKey.value,
                    asSpectator,
                    function (playerToken, gameKey) {
                        that.mainMenu.loading = false;
                        alert('Created');
                        // openGame(playerToken, gameKey);
                    },
                    function (errorMessage) {
                        that.mainMenu.loading = false;
                        that.mainMenu.joinGameForm.validationError = errorMessage;
                    }
                );
            }
        };
    },
    methods: {
        openCreateGameMenu: function () {
            this.mainMenu.formDisplayed = MENU_FORM.CREATE_GAME;
        },
        openJoinGameMenu: function () {
            this.mainMenu.formDisplayed = MENU_FORM.JOIN_GAME;
        },
        backToMainMenu: function () {
            this.mainMenu.formDisplayed = MENU_FORM.MAIN;
            this.mainMenuResetAllForms();
        },
        createGameButtonClicked: function () {
            this.createGame(false);
        },
        createGameAsSpectatorButtonClicked: function () {
            this.createGame(true);
        },
        joinGameButtonClicked: function () {
            this.joinGame(false);
        },
        joinGameAsSpectatorButtonClicked: function () {
            this.joinGame(true);
        },
        createGameTextInputKeyDown: function (e) {
            if (e.keyCode === 13) {
                this.createGameButtonClicked();
            }
        },
        joinGameTextInputKeyDown: function (e) {
            if (e.keyCode === 13) {
                this.joinGameButtonClicked();
            }
        }
    }
});