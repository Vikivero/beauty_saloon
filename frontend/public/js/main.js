$(document).ready(function () {
  let masters = serverData.availableMasters;

  let selectedMasterBusyWindows = {};
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
      } else {
        $(buttonId).prop("disabled", true);
      }
    }
  }

  $("#date")
    .datepicker({
      format: "dd.mm.yyyy",
      startDate: "0d",
      todayBtn: true,
      todayHighlight: true,
      daysOfWeekDisabled: [0, 6],
      closeOnDateSelect: true,
      autoclose: true,
      defaultViewDate: null,
    })
    .on("changeDate", function (e) {
      let currentBusyWindows = selectedMasterBusyWindows["" + e.format()] || [];
      unlockTimeSelectorExceptGiven(currentBusyWindows);
    });
  if (masters && masters.length > 0) {
    masters.forEach(function (master) {
      $("#master").append(
        $("<option>", {
          value: master.masterId,
          text: master.masterName,
        })
      );
    });

    $("#master").on("click", function () {
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

  $('input[name="btnradio"]').click(function () {
    $("#selectedTime").val($(this).attr("id").replace("window", ""));
  });
});
