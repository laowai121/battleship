var BattleshipChat = {
    template: '<div class="chat-container">' +
              '    <div class="chat-history-container">' +
              '        <div v-if="chatLoaded" class="chat-message" v-for="message in messages">' +
              '            <b>{{ message.sender }}</b>: {{ message.message }}' +
              '        </div>' +
              '    </div>' +
              '    <div class="chat-input-container">' +
              '        <input v-on:keydown="inputChatMessageKeydown" v-model="chatMessage" type="text" class="chat-input form-control" />' +
              '        <button v-on:click="sendMessage" type="button" class="btn btn-sm btn-danger send-chat-message-btn">' +
              '            <i class="fa fa-paper-plane"></i>' +
              '        </button>' +
              '    </div>' +
              '</div>',
    data: function() {
        return {
            chatMessage: '',
            messages: [],
            chatLoaded: false
        };
    },
    updated: function () {
        var div = this.$el.querySelector('.chat-history-container');
        div.scrollTop = div.scrollHeight - div.clientHeight;
    },
    methods: {
        populate: function (history) {
            var that = this;

            var existingMessageIds = [];
            this.messages.forEach(function (m) {
                existingMessageIds.push(m.id);
            });

            history.forEach(function (m) {
                if (existingMessageIds.indexOf(m.id) < 0) {
                    that.addRawMessage(m);
                }
            });

            this.messages.sort(function (a, b) {
                var aDateTime = a.dateTime;
                var bDateTime = b.dateTime;

                if (aDateTime < bDateTime) {
                    return -1;
                } else if (bDateTime < aDateTime) {
                    return 1;
                } else {
                    return 0;
                }
            });

            // this.$el.querySelector('.chat-input').focus();
            this.chatLoaded = true;
        },
        addMessage: function (id, dateTime, sender, senderId, message) {
            this.messages.push({
                id: id,
                dateTime:  dateTime,
                sender: sender,
                senderId: senderId,
                message: message
            });
        },
        addRawMessage: function (message) {
            this.addMessage(
                message.id,
                new Date(message.messageTimestamp),
                message.sender,
                message.senderId,
                message.message
            );
        },
        inputChatMessageKeydown: function (e) {
            if (e.keyCode === 13) {
                this.sendMessage();
            }
        },
        sendMessage: function () {
            var that = this;

            if (this.chatMessage.length > 0) {
                var validation = Validators.chatMessageValidator.validate(this.chatMessage);
                if (validation.valid) {
                    battleshipApi.sendChatMessage(
                        battleshipApp.playerToken,
                        this.chatMessage,
                        function (messageId) {
                            that.addMessage(
                                messageId,
                                new Date(),
                                battleshipApp.playerName,
                                battleshipApp.playerId,
                                that.chatMessage
                            );
                            that.chatMessage = '';
                        },
                        function (errorMessage) {
                            battleshipApp.$refs.toast.showToast(errorMessage, {
                                theme: 'error',
                                timeLife: 5000,
                                closeBtn: false
                            });
                        }
                    );
                } else {
                    battleshipApp.$refs.toast.showToast('Unable to send the message: ' + validation.error, {
                        theme: 'error',
                        timeLife: 5000,
                        closeBtn: false
                    });
                }
            }

            this.$el.querySelector('.chat-input').focus();
        }
    }
};