var battleshipLive = {
    subscribe: function (playerToken) {
        var socket = new SockJS('/socket');
        var stompClient = Stomp.over(socket);
        // stompClient.debug = null;

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
        });
    }
};