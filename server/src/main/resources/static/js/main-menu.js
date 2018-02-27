var MENU_FORM = {
    MAIN: 'main',
    CREATE_GAME: 'createGame',
    JOIN_GAME: 'joinGame'
};

var MAX_SPECTATORS_UNLIMITED = -1;

var MainMenu = {
    template: '<div class="battleship-container">' +
              '    <div class="spinner-wrapper" v-show="loading">' +
              '        <i class="spinner fas fa-spinner fa-spin"></i>' +
              '    </div>' +
              '    <div class="main-menu-container" v-show="formDisplayed == MENU_FORM.MAIN">' +
              '        <h2>Battleship Game</h2>' +
              '        <div class="row" style="margin-top: 21px;">' +
              '            <div class="col-md-12">' +
              '                <button v-on:click="openCreateGameMenu()" v-bind:disabled="loading"' +
              '                        type="button" class="btn btn-primary btn-block">Create New Game</button>' +
              '            </div>' +
              '        </div>' +
              '        <div class="row" style="margin-top: 5px;">' +
              '            <div class="col-md-12">' +
              '                <button v-on:click="openJoinGameMenu" v-bind:disabled="loading"' +
              '                        type="button" class="btn btn-warning btn-block">Join Existing Game</button>' +
              '            </div>' +
              '        </div>' +
              '    </div>' +
              '    <div class="create-game-menu-container" v-show="formDisplayed == MENU_FORM.CREATE_GAME">' +
              '        <div class="row" style="margin-bottom: 16px;">' +
              '            <div class="form-group">' +
              '                <label class="col-md-3" for="create-game-player-name">Player</label>' +
              '                <div class="col-md-9">' +
              '                    <input v-model="createGameForm.playerName.value"' +
              '                           v-bind:disabled="loading"' +
              '                           v-bind:class="{ \'validation-error\': createGameForm.playerName.invalid }"' +
              '                           v-on:keydown="createGameTextInputKeyDown"' +
              '                           type="text" class="form-control" id="create-game-player-name"' +
              '                           placeholder="Enter Player Name" />' +
              '                </div>' +
              '            </div>' +
              '        </div>' +
              '        <div class="row" style="margin-bottom: 16px;">' +
              '            <div class="form-group">' +
              '                <label class="col-md-3" for="max-spectators-select">Max Spectators</label>' +
              '                <div class="col-md-9">' +
              '                    <select class="form-control" id="max-spectators-select"' +
              '                            v-model="createGameForm.maxSpectators.value"' +
              '                            v-bind:disabled="loading">' +
              '                        <option value="-1">Unlimited</option>' +
              '                        <option v-for="n in 17" v-bind:value="n - 1">{{ n - 1 }}</option>' +
              '                    </select>' +
              '                </div>' +
              '            </div>' +
              '        </div>' +
              '        <div class="validation-error-container">' +
              '            {{ createGameForm.validationError }}' +
              '        </div>' +
              '        <div class="row">' +
              '            <div class="col-md-12">' +
              '                <button v-bind:disabled="loading"' +
              '                        v-on:click="createGameButtonClicked"' +
              '                        type="button" class="btn btn-primary btn-block">Create Game</button>' +
              '            </div>' +
              '        </div>' +
              '        <div class="row" style="margin-top: 4px;">' +
              '            <div class="col-md-12">' +
              '                <button v-bind:disabled="loading"' +
              '                        v-on:click="createGameAsSpectatorButtonClicked"' +
              '                        type="button" class="btn btn-warning btn-block">Create Game as Spectator</button>' +
              '            </div>' +
              '        </div>' +
              '        <hr class="menu-divider" />' +
              '        <div class="row">' +
              '            <div class="col-md-12">' +
              '                <button v-bind:disabled="loading"' +
              '                        v-on:click="backToMainMenu"' +
              '                        type="button" class="btn btn-danger btn-block">' +
              '                    <i class="fas fa-arrow-left"></i> Back to Main Menu' +
              '                </button>' +
              '            </div>' +
              '        </div>' +
              '    </div>' +
              '    <div class="join-game-menu-container" v-show="formDisplayed == MENU_FORM.JOIN_GAME">' +
              '        <div class="row" style="margin-bottom: 4px;">' +
              '            <div class="form-group">' +
              '                <label class="col-md-2" for="join-game-player-name">Player</label>' +
              '                <div class="col-md-10">' +
              '                    <input v-model="joinGameForm.playerName.value"' +
              '                           v-bind:disabled="loading"' +
              '                           v-bind:class="{ \'validation-error\': joinGameForm.playerName.invalid }"' +
              '                           v-on:keydown="joinGameTextInputKeyDown"' +
              '                           type="text" class="form-control" id="join-game-player-name"' +
              '                           placeholder="Enter Player Name" />' +
              '                </div>' +
              '            </div>' +
              '        </div>' +
              '        <div class="row" style="margin-bottom: 16px;">' +
              '            <div class="form-group">' +
              '                <label class="col-md-2" for="join-game-game-key">Key</label>' +
              '                <div class="col-md-10">' +
              '                    <input v-model="joinGameForm.gameKey.value"' +
              '                           v-bind:disabled="loading"' +
              '                           v-bind:class="{ \'validation-error\': joinGameForm.gameKey.invalid }"' +
              '                           v-on:keydown="joinGameTextInputKeyDown"' +
              '                           type="text" class="form-control" id="join-game-game-key"' +
              '                           placeholder="Enter Game Key" />' +
              '                </div>' +
              '            </div>' +
              '        </div>' +
              '        <div class="validation-error-container">' +
              '            {{ joinGameForm.validationError }}' +
              '        </div>' +
              '        <div class="row">' +
              '            <div class="col-md-12">' +
              '                <button v-bind:disabled="loading"' +
              '                        v-on:click="joinGameButtonClicked"' +
              '                        type="button" class="btn btn-primary btn-block">Join Game</button>' +
              '            </div>' +
              '        </div>' +
              '        <div class="row" style="margin-top: 4px;">' +
              '            <div class="col-md-12">' +
              '                <button v-bind:disabled="loading"' +
              '                        v-on:click="joinGameAsSpectatorButtonClicked"' +
              '                        type="button" class="btn btn-warning btn-block">Join Game as Spectator</button>' +
              '            </div>' +
              '        </div>' +
              '        <hr class="menu-divider" />' +
              '        <div class="row">' +
              '            <div class="col-md-12">' +
              '                <button v-bind:disabled="loading"' +
              '                        v-on:click="backToMainMenu"' +
              '                        type="button" class="btn btn-danger btn-block">' +
              '                    <i class="fas fa-arrow-left"></i> Back to Main Menu' +
              '                </button>' +
              '            </div>' +
              '        </div>' +
              '    </div>' +
              '</div>',

    data: function () {
        return {
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

        this.resetAllForms = function () {
            that.createGameForm.playerName.value = '';
            that.createGameForm.playerName.invalid = false;
            that.createGameForm.maxSpectators.value = MAX_SPECTATORS_UNLIMITED;
            that.joinGameForm.playerName.value = '';
            that.joinGameForm.playerName.invalid = false;
            that.joinGameForm.gameKey.value = '';
            that.joinGameForm.gameKey.invalid = false;
            that.createGameForm.validationError = '';
            that.joinGameForm.validationError = '';
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
            var formValid = that.validateForm(that.createGameForm);
            var playerName = that.createGameForm.playerName.value;

            if (formValid) {
                that.loading = true;

                battleshipApi.createGame(
                    playerName,
                    that.createGameForm.maxSpectators.value,
                    asSpectator,
                    function (playerToken, playerId, gameKey) {
                        that.loading = false;
                        that.$root.openGame(playerToken, playerId, gameKey, playerName);
                    },
                    function (errorMessage) {
                        that.loading = false;
                        that.createGameForm.validationError = errorMessage;
                    }
                );
            }
        };

        this.joinGame = function (asSpectator) {
            var formValid = that.validateForm(that.joinGameForm);
            var playerName = that.joinGameForm.playerName.value;

            if (formValid) {
                that.loading = true;

                battleshipApi.joinGame(
                    playerName,
                    that.joinGameForm.gameKey.value,
                    asSpectator,
                    function (playerToken, playerId, gameKey) {
                        that.loading = false;
                        that.$root.openGame(playerToken, playerId, gameKey, playerName);
                    },
                    function (errorMessage) {
                        that.loading = false;
                        that.joinGameForm.validationError = errorMessage;
                    }
                );
            }
        }
    },

    methods: {
        openCreateGameMenu: function () {
            this.formDisplayed = MENU_FORM.CREATE_GAME;
        },
        openJoinGameMenu: function () {
            this.formDisplayed = MENU_FORM.JOIN_GAME;
        },
        backToMainMenu: function () {
            this.formDisplayed = MENU_FORM.MAIN;
            this.resetAllForms();
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
};