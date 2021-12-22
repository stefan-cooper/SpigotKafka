import { useEffect, useState } from 'react'

import './App.css';
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';
  import 'react-toastify/dist/ReactToastify.css';

const App = () => {

  const [notifications, updateNotifications] = useState([])

  console.log(notifications)

  useEffect(() => {
    setInterval(() => {
      getNotifications()
    }, 10000)
  }, [])

  const getNotifications = () => {
    axios.get(`http://localhost:3001/api/kafka_poll`).then(res => updateNotifications(res.data))
  }

  return (
    <>
    <ToastContainer
        position="top-center"
        autoClose={false}
        hideProgressBar
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
    />
    <div className="App">

      <header className="App-header">
        <p>
          Kafka Minecraft Motion Detector
        </p>

      </header>
      <div className="notifs">
        {
          notifications.reverse().forEach((notif, index) => {
            console.log(notif)
            if (index < 5) {
              toast(notif, {
                position: "top-center",
                autoClose: false,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                toastId: notif
              })}
            })
      }
      </div>

    </div>
    </>
  );
}

export default App;
