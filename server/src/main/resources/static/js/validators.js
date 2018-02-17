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