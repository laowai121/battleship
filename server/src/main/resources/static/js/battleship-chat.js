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
        load: function (history) {
            var that = this;

            var existingSequenceNumbers = [];
            this.messages.forEach(function (m) {
                existingSequenceNumbers.push(m.sequenceNumber);
            });

            history.forEach(function (m) {
                if (existingSequenceNumbers.indexOf(m.sequenceNumber) < 0) {
                    that.addMessage(m);
                }
            });

            this.messages.sort(function (a, b) {
                var aSequenceNumber = a.sequenceNumber;
                var bSequenceNumber = b.sequenceNumber;
                var aDateTime = a.dateTime;
                var bDateTime = b.dateTime;

                if (aSequenceNumber < bSequenceNumber) {
                    if (aDateTime > bDateTime) {
                        console.error('Messages out of order!');
                    }
                } else if (bSequenceNumber < aSequenceNumber) {
                    if (bDateTime > aDateTime) {
                        console.error('Messages out of order!');
                    }
                } else {
                    console.error('Duplicate message sequence numbers!');
                }
            });

            // this.$el.querySelector('.chat-input').focus();
            this.chatLoaded = true;
        },
        addMessage: function (message) {
            this.messages.push({
                sequenceNumber: message.sequenceNumber,
                dateTime:  new Date(message.messageTimestamp),
                sender: message.sender,
                message: message.message
            });
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
                        function (messageSequenceNumber) {
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