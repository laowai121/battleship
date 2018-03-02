var GAME_API_URL_PREFIX = '/game';
var CHAT_API_URL_PREFIX = '/chat';

var battleshipApi = {
    createGame: function (playerName, maxSpectators, joinAsSpectator, success, error) {
        $.ajax({
            url: GAME_API_URL_PREFIX + '/create?playerName=' + encodeURIComponent(playerName) + '&maxSpectators=' + maxSpectators + '&joinAsSpectator=' + joinAsSpectator,
            type: 'POST',
            success: function (result) {
                if (result.success && success) {
                    success(
                        result.data.playerToken,
                        result.data.playerId,
                        result.data.gameKey,
                        result.data.isPlayerA
                    );
                } else if (error) {
                    error(result.data.errorMessage || 'Error while creating the game. Check your internet connection');
                }
            },
            error: function (result) {
                if (error) {
                    error('Error while creating the game. Check your internet connection');
                }
            }
        });
    },
    joinGame: function (playerName, gameKey, joinAsSpectator, success, error) {
        $.ajax({
            url: GAME_API_URL_PREFIX + '/join?playerName=' + encodeURIComponent(playerName) + '&gameKey='
                    + encodeURIComponent(gameKey) + '&joinAsSpectator=' + joinAsSpectator,
            type: 'POST',
            success: function (result) {
                if (result.success && success) {
                    success(
                        result.data.playerToken,
                        result.data.playerId,
                        result.data.gameKey,
                        result.data.isPlayerA
                    );
                } else if (error) {
                    error(result.data.errorMessage || 'Error while joining the game. Check your internet connection');
                }
            },
            error: function (result) {
                if (error) {
                    error('Error while joining the game. Check your internet connection');
                }
            }
        });
    },
    subscribeToLiveUpdates: function (stompClient, playerToken, handler) {
        stompClient.subscribe('/battleship/' + playerToken, function (o) {
            handler(o);
        });
    },
    subscribeToChatUpdates: function (stompClient, playerToken, handler) {
        stompClient.subscribe('/chat/' + playerToken, function (o) {
            handler(o);
        });
    },
    loadChatHistory: function (playerToken, success, error) {
        $.ajax({
            url: CHAT_API_URL_PREFIX + '/history?playerToken=' + encodeURIComponent(playerToken),
            type: 'GET',
            success: function (result) {
                if (result.success && success) {
                    success(result.data.chatHistory);
                } else if (error) {
                    error(result.data.errorMessage);
                }
            },
            error: function (result) {
                if (error) {
                    error('Error while loading chat history. Check your internet connection');
                }
            }
        });
    },
    sendChatMessage: function (playerToken, message, success, error) {
        $.ajax({
            url: CHAT_API_URL_PREFIX + '/send',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                playerToken: playerToken,
                message: message
            }),
            success: function (result) {
                if (result.success && success) {
                    success(result.data.messageId);
                } else if (error) {
                    error(result.data.errorMessage);
                }
            },
            error: function (result) {
                if (error) {
                    error('Error while sending the message. Check your internet connection');
                }
            }
        });
    },
    getGameState: function (playerToken, success, error) {
        $.ajax({
            url: GAME_API_URL_PREFIX + '/getState?playerToken=' + encodeURIComponent(playerToken),
            type: 'GET',
            success: function (result) {
                if (result.success && success) {
                    success(result.data.gameState);
                } else if (error) {
                    error(result.data.errorMessage);
                }
            },
            error: function (result) {
                if (error) {
                    error('Error while obtaining the game state. Check your internet connection');
                }
            }
        });
    },
    getExtendedGameState: function (playerToken, success, error) {
        $.ajax({
            url: GAME_API_URL_PREFIX + '/getExtendedState?playerToken=' + encodeURIComponent(playerToken),
            type: 'GET',
            success: function (result) {
                if (result.success && success) {
                    success(result.data.extendedGameState);
                } else if (error) {
                    error(result.data.errorMessage);
                }
            },
            error: function (result) {
                if (error) {
                    error('Error while obtaining the game state. Check your internet connection');
                }
            }
        });
    }
};