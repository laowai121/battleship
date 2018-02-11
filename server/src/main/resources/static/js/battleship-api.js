var battleshipApi = {
    createAndJoinGame: function (playerName, joinAsSpectator, success, error) {
        $.ajax({
            url: '/battleship/createAndJoin?playerName=' + encodeURIComponent(playerName) + '&joinAsSpectator=' + joinAsSpectator,
            type: 'POST',
            success: function (result) {
                if (result.success) {
                    success(result.data.playerToken, result.data.gameKey);
                } else {
                    error(result.data.errorMessage || 'Error while creating the game. Check your internet connection')
                }
            },
            error: function (result) {
                error('Error while creating the game. Check your internet connection');
            }
        });
    },
    joinGame: function (playerName, gameKey, joinAsSpectator, success, error) {
        $.ajax({
            url: '/battleship/join?playerName=' + encodeURIComponent(playerName) + '&gameKey='
                    + encodeURIComponent(gameKey) + '&joinAsSpectator=' + joinAsSpectator,
            type: 'POST',
            success: function (result) {
                if (result.success) {
                    success(result.data.playerToken, result.data.gameKey);
                } else {
                    error(result.data.errorMessage || 'Error while joining the game. Check your internet connection')
                }
            },
            error: function (result) {
                error('Error while joining the game. Check your internet connection');
            }
        });
    }
};