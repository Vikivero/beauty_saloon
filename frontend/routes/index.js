const express = require("express");
const { check, validationResult } = require("express-validator");

const router = express.Router();

router.get("/", (req, res) => {
  res.render("index", { title: "Index page" });
});

// Placeholder server data
const serverData = {
  datesForDisable: ["25.04.2024", "27.04.2024"],
  availableMasters: {
    "22.04.2024": [
      { id: 0, name: "Master 1" },
      { id: 1, name: "Master 2" },
      { id: 2, name: "Master 3" },
    ],
  },
};

router.get("/register", (req, res) => {
  // Get masters data from backend
  // show calendar with free days
  // todo: get server data

  res.render("register", {
    title: "Register Appointment",
    serverData: serverData,
  });
});

router.post(
  "/register",
  [
    check("name").isLength({ min: 1 }).withMessage("Please enter a name"),
    check("email").isLength({ min: 1 }).withMessage("Please enter an email"),
  ],
  (req, res) => {
    const errors = validationResult(req);
    // todo: get server data
    if (errors.isEmpty()) {
      res.send("Thank you for your registration!");
    } else {
      res.render("form", {
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

module.exports = router;
