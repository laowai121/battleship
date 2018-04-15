var BATTLESHIP_UPDATE_TYPE = {
    PLAYER_JOINED: 'PLAYER_JOINED',
    SPECTATOR_JOINED: 'SPECTATOR_JOINED'
};

var battleshipLive = {
    subscribe: function (playerToken) {
        this.reconnect(playerToken);
    },
    reconnect: function (playerToken) {
        var that = this;

        var socket = new SockJS('/socket');
        var stompClient = Stomp.over(socket);

        stompClient.connect({}, function () {
            battleshipApi.subscribeToLiveUpdates(stompClient, playerToken, function (update) {
                try {
                    update = JSON.parse(update.body);
                } catch (e) {
                    console.error(e);
                    return;
                }

                if (update.type === BATTLESHIP_UPDATE_TYPE.PLAYER_JOINED) {
                    battleshipApp.playerJoined(update.properties.player)
                } else if (update.type === BATTLESHIP_UPDATE_TYPE.SPECTATOR_JOINED) {
                    battleshipApp.spectatorJoined(update.properties.spectator)
                }

                battleshipApp.reloadGameState();
            });

            battleshipApi.subscribeToChatUpdates(stompClient, playerToken, function (update) {
                try {
                    battleshipApp.$refs.chat.addRawMessage(JSON.parse(update.body));
                } catch (e) {
                    console.error(e);
                }
            });
        }, function () {
            setTimeout(function () {
                that.reconnect(playerToken);
            }, 1000);
        });
    }
};