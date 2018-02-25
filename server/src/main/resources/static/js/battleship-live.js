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

                alert(update);
            });

            battleshipApi.subscribeToChatUpdates(stompClient, playerToken, function (update) {
                try {
                    battleshipApp.$refs.chat.addMessage(JSON.parse(update.body));
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