var BattleshipLive = {
    subscribe: function (playerToken) {
        battleshipApi.subscribeToLiveUpdates(playerToken, function () {

        }, function () {

        });
    }
};