const express = require("express");
const { check, validationResult } = require("express-validator");
const axios = require("axios");

const router = express.Router();

router.get("/", (req, res) => {
  res.render("index", { title: "Index page" });
});

// Placeholder server data
// const serverData = {
//   datesForDisable: ["25.04.2024", "27.04.2024"],
//   availableMasters: [
//     {
//       masterId: 0,
//       masterName: "Beauty Master 0",
//       busyWindows: {
//         "22.04.2024": [0, 1, 2, 3, 4, 5, 6, 7, 8],
//       },
//     },
//     {
//       masterId: 1,
//       masterName: "Beauty Master 1",
//       busyWindows: {
//         "22.04.2024": [0, 2, 4, 6, 8],
//         "25.04.2024": [0, 1, 2, 3, 4, 5, 6, 7, 8],
//       },
//     },
//     {
//       masterId: 2,
//       masterName: "Beauty Master 2",
//       busyWindows: {
//         "22.04.2024": [0, 3, 6],
//         "26.04.2024": [0, 1, 2, 3, 4, 5, 6, 7, 8],
//       },
//     },
//     {
//       masterId: 3,
//       masterName: "Beauty Master 3",
//       busyWindows: {
//         "22.04.2024": [1, 2, 3, 4, 8],
//         "23.04.2024": [0, 1, 2, 3, 4, 5, 6, 7, 8],
//         "24.04.2024": [0, 1, 2, 3, 4, 5, 6, 7, 8],
//       },
//     },
//   ],
// };

let serverData = {};

router.get("/register", (req, res) => {
  // Get masters data from backend
  // show calendar with free days

  axios
    .get("http://localhost:8080/api/master-appointments")
    .then((response) => {
      serverData = response.data;
      res.render("register", {
        title: "Register Appointment",
        serverData: serverData,
      });
    })
    .catch((error) => {
      console.log(error);
      res.render("register", {
        title: "Register Appointment",
        serverData: {},
      });
    });
});

router.post(
  "/register",
  [
    check("user").isLength({ min: 1 }).withMessage("Please enter a name"),
    check("master").isInt().withMessage("Please select a master"),
    check("date")
      .notEmpty()
      .not()
      .equals("yyyy-mm-dd")
      .withMessage("Please select a date"),
    check("selectedTime")
      .isInt()
      .withMessage("Please select an appointment time"),
  ],
  (req, res) => {
    const errors = validationResult(req);
    // todo: get server data
    if (errors.isEmpty()) {
      // Data recieved from form:
      // { "user": "sdff", "master": "0", "date": "2024-04-24", "btnradio": "on", "selectedTime": "4" }
      let enteredData = req.body;
      let requestData = {
        patientName: enteredData.user,
        date: enteredData.date,
        appointmentWindow: enteredData.selectedTime,
        masterId: enteredData.master,
      };

      axios
        .post("http://localhost:8080/api/register", requestData)
        .then((response) => {
          let responseBody = response.data;
          let appointmentId = responseBody;
          console.log("Appointment created. Id: " + appointmentId);

          res.render("check", {
            title: "Check/Remove Appointment",
            data: { appointmentId: appointmentId } || {},
          });
        })
        .catch((error) => {
          console.error("Error creating appointment: " + error);

          let errors = [
            { msg: "Appointment Already Registered or Connection Error" },
          ];

          axios
            .get("http://localhost:8080/api/master-appointments")
            .then((response) => {
              let serverData = response.data;
              res.render("register", {
                title: "Register Appointment",
                serverData: serverData,
              });
            })
            .catch((error) => {
              console.log(error);
              res.render("register", {
                title: "Register Appointment",
                serverData: {},
              });
            });
          res.render("register", {
            title: "Registration form",
            errors: errors,
            userData: req.body,
            serverData: serverData,
          });
        });
    } else {
      res.render("register", {
        title: "Registration form",
        errors: errors.array(),
        userData: req.body,
        serverData: serverData,
      });
    }
  }
);

router.get("/check", (req, res) => {
  res.render("check", { title: "Check/Remove Appointment" });
});

router.post("/check", (req, res) => {
  // get data from backend

  let appointmentId = req.body.ticketId;
  axios
    .get(`http://localhost:8080/api/appointment/${appointmentId}`)
    .then((response) => {
      let responseBody = response.data;
      console.log(response.data);
      let timeWindows = [
        "08:00 - 08:45",
        "09:00 - 09:45",
        "10:00 - 10:45",
        "11:00 - 11:45",
        "12:00 - 12:45",
        "13:00 - 13:45",
        "14:00 - 14:45",
        "15:00 - 15:45",
        "16:00 - 16:45",
      ];

      data = {
        id: responseBody.id,
        user: responseBody.patientName,
        master: responseBody.masterName,
        date: responseBody.date,
        time: timeWindows[responseBody.appointmentWindow],
      };
      res.render("details", {
        title: "Visit Details",
        data: data || {},
      });
    })
    .catch((error) => {
      console.log(error);
    });

  // data = {
  //   id: req.body.ticketId,
  //   user: "John",
  //   master: "Master 1",
  //   date: "22.04.2024",
  //   time: "10:00",
  // };
  // res.render("details", {
  //   title: "Visit Details",
  //   data: data || {},
  // });
});

router.get("/details", (req, res) => {
  res.render("details", { title: "Visit Details" });
});

router.get("/cancel", (req, res) => {
  let visitId = req.query.id;
  // todo: remove visit from backend
  res.render("details", {
    title: "Visit Details",
    data: { id: visitId, cancelled: true },
  });
});

module.exports = router;
