$(document).ready(function () {
  $("#date")
    .datepicker({
      format: "dd.mm.yyyy",
      startDate: "0d",
      todayBtn: true,
      todayHighlight: true,
      daysOfWeekDisabled: [0, 6],
      datesDisabled: serverData.datesForDisable,
      closeOnDateSelect: true,
      autoclose: true,
    })
    .on("changeDate", function (e) {
      // Remove all options from the dropdown #master
      $("#master").empty();
      // Add options from serverData.masters to the dropdown #master

      var masters = serverData.availableMasters[e.format()];
      if (masters && masters.length > 0) {
        masters.forEach(function (master) {
          $("#master").append(
            $("<option>", {
              value: master.id,
              text: master.name,
            })
          );
        });
        $("#master").removeAttr("disabled");
      } else {
        // If no masters available, add only one option "No masters available"
        $("#master").append(
          $("<option>", {
            value: "",
            text: "No masters available",
          })
        );
        $("#master").addAttr("disabled");
      }
    });
});
