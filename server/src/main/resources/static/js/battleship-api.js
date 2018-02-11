var battleshipApi = {
    createAndJoinGame: function (playerName, joinAsSpectator, success, error) {
        $.ajax({
            url: '/battleship/createAndJoin?playerName=' + encodeURIComponent(playerName) + '&joinAsSpectator=' + joinAsSpectator,
            type: 'POST',
            success: function (result) {
                success();
            },
            error: function (result) {
                error({ errorMessage: 'Unable to join the game' });
            }
        });
    }
};