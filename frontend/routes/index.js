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

router.get("/register", (req, res) => {
  // Get masters data from backend
  // show calendar with free days

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
      // res.send(req.body);

      // todo: send data to backend and get appointmentId
      let appointmentId = 10033;

      res.render("check", {
        title: "Check/Remove Appointment",
        data: { appointmentId: appointmentId } || {},
      });
      // res.send("Thank you for your registration!");
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
  data = {
    id: req.body.ticketId,
    user: "John",
    master: "Master 1",
    date: "22.04.2024",
    time: "10:00",
  };
  res.render("details", {
    title: "Visit Details",
    data: data || {},
  });
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
