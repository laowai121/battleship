(function () {
    function GameMenu(battleshipGame) {
        var MENU_FORM = {
            MAIN: 'main',
            CREATE_GAME: 'createGame',
            JOIN_GAME: 'joinGame'
        };

        function MenuFormController(gameMenuContainer) {
            var menuContainers, inputsWithValidation, validationErrorContainers, loadingSpinner;
            var allMenuInputs, allMenuTextInputs;

            var displayedMenuForm = MENU_FORM.MAIN;

            init();

            var that = this;

            this.openCreateGameMenu = function () {
                displayedMenuForm = MENU_FORM.CREATE_GAME;
                refresh();
            };

            this.openJoinGameMenu = function () {
                displayedMenuForm = MENU_FORM.JOIN_GAME;
                refresh();
            };

            this.backToMainMenu = function () {
                resetFormInputs();
                that.hideValidationErrors();
                displayedMenuForm = MENU_FORM.MAIN;
                refresh();
            };

            this.hideValidationErrors = function () {
                inputsWithValidation.removeClass('validation-error');
                validationErrorContainers.html('');
                validationErrorContainers.hide();
            };

            this.startLoading = function () {
                allMenuInputs.attr('disabled', 'disabled');
                loadingSpinner.show();
            };

            this.stopLoading = function () {
                allMenuInputs.removeAttr('disabled');
                loadingSpinner.hide();
            };

            function resetFormInputs() {
                allMenuTextInputs.val('');
            }

            function refresh() {
                menuContainers.hide();
                menuContainers.filter('[data-menu-form="' + displayedMenuForm + '"]').show();
            }

            function initVariables() {
                menuContainers = gameMenuContainer.find('.menu-container');
                inputsWithValidation = gameMenuContainer.find('.input-with-validation');
                validationErrorContainers = gameMenuContainer.find('.validation-error-container');
                loadingSpinner = $('#loading-spinner');
                allMenuInputs = gameMenuContainer.find('.menu-input');
                allMenuTextInputs = allMenuInputs.filter('[type="text"]');
            }

            function init() {
                initVariables();
            }
        }

        var menuFormController;
        var gameMenuContainer;
        var createGameMenuButton, joinGameMenuButton, backToMainMenuButtons;
        var createGameButton, createGameAsSpectatorButton;
        var createGamePlayerName, createGameErrorContainer, maxSpectatorsSelect;
        var joinGameButton, joinGameAsSpectatorButton;
        var joinGamePlayerName, /*joinGameGameId,*/ joinGameGameKey, joinGameErrorContainer;

        var that = this;

        this.hide = function () {
            gameMenuContainer.hide();
        };

        function createGameButtonClicked(joinAsSpectator) {
            var playerName = createGamePlayerName.val().trim().replace(/\s\s+/g, ' ');
            var maxSpectators = parseInt(maxSpectatorsSelect.val());
            var validationStatus = validatePlayerName(playerName);

            if (validationStatus.valid) {
                removeValidationError(createGamePlayerName, createGameErrorContainer);
                createGame(playerName, maxSpectators, joinAsSpectator);
            } else {
                displayValidationError(createGamePlayerName, createGameErrorContainer, validationStatus.message);
            }
        }

        function createGame(playerName, maxSpectators, joinAsSpectator) {
            menuFormController.startLoading();
            battleshipApi.createGame(playerName, maxSpectators, joinAsSpectator, function (playerToken, gameKey) {
                openGame(playerToken, gameKey);
            }, function (errorMessage) {
                menuFormController.stopLoading();
                displayErrorMessage(createGameErrorContainer, errorMessage);
            });
        }

        function joinGame(playerName, gameKey, joinAsSpectator) {
            menuFormController.startLoading();
            battleshipApi.joinGame(playerName, gameKey, joinAsSpectator, function (playerToken, gamekey) {
                openGame(playerToken, gameKey);
            }, function (errorMessage) {
                menuFormController.stopLoading();
                displayErrorMessage(joinGameErrorContainer, errorMessage);
            });
        }

        function openGame(playerToken, gameKey) {
            battleshipGame.loadGame(playerToken, gameKey);
        }

        function joinGameButtonClicked(joinAsSpectator) {
            var playerName = joinGamePlayerName.val().trim().replace(/\s\s+/g, ' ');
            // var gameId = joinGameGameId.val().trim();
            var gameKey = joinGameGameKey.val().trim().toLowerCase();

            var i;

            var validationStatusArr = [
                { status: validatePlayerName(playerName), element: joinGamePlayerName },
                // { status: validateGameId(playerName), element: joinGameGameId },
                { status: validateGameKey(gameKey), element: joinGameGameKey }
            ];

            var validation;
            var inputValid = true;

            removeValidationError(joinGamePlayerName.add(joinGameGameKey).add(joinGameErrorContainer), joinGameErrorContainer);

            for (i = 0; i < validationStatusArr.length; i++) {
                validation = validationStatusArr[i];
                if (!validation.status.valid) {
                    inputValid = false;
                    break;
                }
            }

            if (inputValid) {
                // removeErrorMessage(joinGamePlayerName.add(joinGameGameId).add(joinGameGameKey).add(joinGameErrorContainer));
                // joinGame(playerName, gameId, gameKey, joinAsSpectator);
                joinGame(playerName, gameKey, joinAsSpectator);
            } else {
                displayValidationError(validation.element, joinGameErrorContainer, validation.status.message);
            }
        }

        function displayValidationError(element, errorMessageContainer, errorMessage) {
            element.addClass('validation-error');
            displayErrorMessage(errorMessageContainer, errorMessage);
        }

        function displayErrorMessage(errorMessageContainer, errorMessage) {
            errorMessageContainer.html(errorMessage);
            errorMessageContainer.show();
        }

        function removeValidationError(element, errorMessageContainer) {
            element.removeClass('validation-error');
            removeErrorMessage(errorMessageContainer);
        }

        function removeErrorMessage(errorMessageContainer) {
            errorMessageContainer.html('');
            errorMessageContainer.hide();
        }

        function validatePlayerName(playerName) {
            var result = {
                valid: false,
                message: ''
            };

            if (playerName.length < 1) {
                result.message = 'Please, enter player name and try again';
            } else if (playerName.length > 20) {
                result.message = 'Player name is too long (maximum length: 20). Please, enter valid player name and try again'
            } else {
                result.valid = true;
            }

            return result;
        }

        function validateGameKey(gameKey) {
            var result = {
                valid: false,
                message: ''
            };

            if (gameKey.length < 1) {
                result.message = 'Please, enter game key and try again';
            } else if (gameKey.length != 16 || !/^[a-f0-9]+$/.test(gameKey)) {
                result.message = 'Invalid Game Key. Game Key should be a sequence of lower case alphanumerics of length 16'
            } else {
                result.valid = true;
            }

            return result;
        }

        function initVariables() {
            gameMenuContainer = $('#game-menu-container');
            createGameMenuButton = $('#create-game-menu-button');
            joinGameMenuButton = $('#join-game-menu-button');
            backToMainMenuButtons = gameMenuContainer.find('.back-to-main-menu-button');
            createGameButton = $('#create-game-button');
            createGameAsSpectatorButton = $('#create-game-as-spectator-button');
            createGamePlayerName = $('#create-game-player-name');
            createGameErrorContainer = $('#create-game-error-container');
            joinGameButton = $('#join-game-button');
            joinGameAsSpectatorButton = $('#join-game-as-spectator-button');
            joinGamePlayerName = $('#join-game-player-name');
            // joinGameGameId = $('#join-game-game-id');
            joinGameGameKey = $('#join-game-game-key');
            joinGameErrorContainer = $('#join-game-error-container');
            maxSpectatorsSelect = $('#max-spectators-select');

            menuFormController = new MenuFormController(gameMenuContainer);
        }

        function addEventListeners() {
            createGameMenuButton.bind('click', menuFormController.openCreateGameMenu);
            joinGameMenuButton.bind('click', menuFormController.openJoinGameMenu);
            backToMainMenuButtons.bind('click', menuFormController.backToMainMenu);

            createGamePlayerName.bind('keydown', function (e) {
                if (e.keyCode == 13) {
                    createGameButtonClicked(false);
                }
            });

            joinGamePlayerName.add(joinGameGameKey).bind('keydown', function (e) {
                if (e.keyCode == 13) {
                    joinGameButtonClicked(false);
                }
            });

            createGameButton.bind('click', function () {
                createGameButtonClicked(false);
            });

            createGameAsSpectatorButton.bind('click', function () {
                createGameButtonClicked(true);
            });

            joinGameButton.bind('click', function () {
                joinGameButtonClicked(false);
            });

            joinGameAsSpectatorButton.bind('click', function () {
                joinGameButtonClicked(true);
            });
        }

        function init() {
            initVariables();
            addEventListeners();
            battleshipGame.gameMenu = that;
        }

        init();
    }

    function BattleshipGame() {
        var gameContainer;

        this.gameMenu = null;

        var that = this;

        this.loadGame = function (playerToken, gameKey) {
            that.gameMenu.hide();

            // gameContainer.html('<h3>Connected. Player Token: ' + playerToken
            //     + ', Game Key: ' + gameKey + '</h3>');

            gameContainer.show();
        };

        function init() {
            gameContainer = $('#game-container');
        }

        init();
    }

    var gameMenu, battleshipGame;

    function init() {
        battleshipGame = new BattleshipGame();
        gameMenu = new GameMenu(battleshipGame);
    }

    $(function () {
        init();
    });
})();