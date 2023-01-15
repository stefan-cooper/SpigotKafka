import { useEffect, useState } from 'react'

import './App.css';
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import addNotification, { Notifications } from 'react-push-notification'

const App = () => {
  const [notifiedSet, updateNotifiedSet] = useState(new Set())
  const timeInterval = 1000

  useEffect(() => {
    setInterval(() => {
      getNotifications()
    }, timeInterval)
  }, [])

  const getNotifications = () => {
    const updateNotifs = (notifs, type) => {
      const newNotifications = notifs.map((notif) => { return { type, ...JSON.parse(notif)}}).filter(notif => !notifiedSet.has(notif.id))
      newNotifications.forEach((notification) => {
        const stringified = JSON.stringify(notification, null, 2)
        notifiedSet.add(notification.id)
        toast(stringified, {
          position: "top-center",
          autoClose: false,
          hideProgressBar: true,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          toastId: notification.id
        })
        renderNotification(stringified)
      })
      updateNotifiedSet(notifiedSet)
    }

    const timeCheck = new Date().getTime() - timeInterval

    axios.get(`http://localhost:3001/api/kafka_motion?time=${timeCheck}`).then(res => {
      if (res && res.data) updateNotifs(res.data, "motion")
    })
    axios.get(`http://localhost:3001/api/kafka_login?time=${timeCheck}`).then(res => {
      if (res && res.data) updateNotifs(res.data, "login")
    })
    axios.get(`http://localhost:3001/api/kafka_ring?time=${timeCheck}`).then(res => {
      if (res && res.data) updateNotifs(res.data, "ring")
    })
    axios.get(`http://localhost:3001/api/kafka_chat?time=${timeCheck}`).then(res => {
      if (res && res.data) updateNotifs(res.data, "chat")
    })
    axios.get(`http://localhost:3001/api/kafka_death?time=${timeCheck}`).then(res => {
      if (res && res.data) updateNotifs(res.data, "death")
    })
    axios.get(`http://localhost:3001/api/kafka_disconnect?time=${timeCheck}`).then(res => {
      if (res && res.data) updateNotifs(res.data, "disconnect")
    })
  }

  const renderNotification = (notif) => {
    addNotification({
      native: true,
      title: notif
    })
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
    <Notifications />
    <div className="App">
      <header className="App-header">
        <p>Kafka Minecraft Events</p>
      </header>
    </div>
    </>
  );
}

export default App;
