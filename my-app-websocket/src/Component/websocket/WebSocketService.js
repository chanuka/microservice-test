import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

const WebSocketService = {
  connect: (callback) => {
    const socket = new SockJS('http://localhost:8080/my-socket/gs-guide-websocket');
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
      callback(stompClient);
    });
    console.log('Web socket connected.............................');
  },

  subscribe: (stompClient, destination, callback) => {
    stompClient.subscribe(destination, (message) => {
      callback(message.body);
    });
    console.log(
      "I'm Listening to : " + destination + '........................'
    );
  },

  sendMessage: (stompClient, destination, message) => {
    stompClient.send(destination, {}, JSON.stringify(message));
    console.log(
      'Message sent to ' + destination + ': ' + JSON.stringify(message)
    );
  },

  disconnect: (stompClient) => {
    if (stompClient) {
      stompClient.disconnect();
    }
  },
};

export default WebSocketService;
