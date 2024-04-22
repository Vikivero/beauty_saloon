$(document).ready(function () {
  let masters = serverData.availableMasters;

  let selectedMasterBusyWindows = {};

  $("#date")
    .datepicker({
      format: "dd.mm.yyyy",
      startDate: "0d",
      todayBtn: true,
      todayHighlight: true,
      daysOfWeekDisabled: [0, 6],
      closeOnDateSelect: true,
      autoclose: true,
    })
    .on("changeDate", function (e) {
      let currentBusyWindows = selectedMasterBusyWindows["" + e.format()] || [];
      // if (currentBusyWindows && currentBusyWindows.length > 0) {
      //   currentBusyWindows.forEach(function (index) {
      //     var buttonId = "#window" + index;
      //     $(buttonId).prop("disabled", true);
      //   });
      // }
      unlockTimeSelectorExceptGiven(currentBusyWindows);
    });
  if (masters && masters.length > 0) {
    $("#master").append(
      $("<option>", {
        text: "Select your master",
        selected: true,
        disabled: true,
      })
    );
    masters.forEach(function (master) {
      $("#master").append(
        $("<option>", {
          value: master.masterId,
          text: master.masterName,
        })
      );
    });

    function lockTimeSelector() {
      for (i = 0; i < 8; i++) {
        var buttonId = "#window" + i;
        $(buttonId).prop("disabled", true);
      }
    }

    function unlockTimeSelectorExceptGiven(indexes) {
      for (i = 0; i < 8; i++) {
        var buttonId = "#window" + i;
        if (!indexes.includes(i)) {
          $(buttonId).prop("disabled", false);
        }
      }
    }

    $("#master").on("change", function () {
      lockTimeSelector();
      $("#date").val("dd.mm.yyyy").datepicker("update");
      let selectedMasterId = $(this).find(":selected").val();
      let master = masters.find(function (master) {
        return master.masterId == selectedMasterId;
      });
      let fullyBusyDates = Object.keys(master.busyWindows).filter(function (
        key
      ) {
        return [0, 1, 2, 3, 4, 5, 6, 7, 8].every(function (element) {
          return master.busyWindows[key].includes(element);
        });
      });

      selectedMasterBusyWindows = master.busyWindows;

      $("#date").datepicker("setDatesDisabled", fullyBusyDates);
      $("#date").removeAttr("disabled");
    });
  } else {
    // If no masters available, display only one option "No masters available"
    $("#master").clear();
    $("#master").append(
      $("<option>", {
        value: "",
        text: "No masters available",
      })
    );
    $("#master").attr("disabled", "disabled");
  }
});
