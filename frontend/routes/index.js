const express = require("express");
const { check, validationResult } = require("express-validator");

const router = express.Router();

router.get("/", (req, res) => {
  res.render("index", { title: "Index page" });
});

router.get("/register", (req, res) => {
  res.render("register", { title: "Register Appointment" });
});

router.get("/check", (req, res) => {
  res.render("check", { title: "Check/Remove Appointment" });
});

router.post(
  "/",
  [
    check("name").isLength({ min: 1 }).withMessage("Please enter a name"),
    check("email").isLength({ min: 1 }).withMessage("Please enter an email"),
  ],
  (req, res) => {
    const errors = validationResult(req);

    if (errors.isEmpty()) {
      res.send("Thank you for your registration!");
    } else {
      res.render("form", {
        title: "Registration form",
        errors: errors.array(),
        data: req.body,
      });
    }
  }
);

module.exports = router;
