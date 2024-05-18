import React, { useEffect, useState } from 'react';
import WebSocketService from '../websocket/WebSocketService';
import './Home.css';

function App() {
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    WebSocketService.connect((stompClient) => {
      WebSocketService.subscribe(stompClient, '/topic/greetings', (message) => {
        setMessages((prevMessages) => {
          if (!prevMessages.includes(message)) {
            return [...prevMessages, message];
          }
          return prevMessages;
        });
      });

      const messageToSend = {
       "name" : "Chanuka"
      };
      WebSocketService.sendMessage(
        stompClient,
        '/app/hello',
        messageToSend
      );
    });

    return () => {
      WebSocketService.disconnect();
    };
  }, []);

  return (
    <div>
      <h1 className="title">List of Transaction</h1>
      <div className="message-container">
        {messages.map((message, index) => (
          <div key={index} className="message">
            {index + 1} : {message}
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
