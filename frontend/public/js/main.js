// Temp placeholder
// data = {};
// data.datesForDisable = ["25.04.2024", "27.04.2024"];

$(document).ready(function () {
  $("#date")
    .datepicker({
      format: "dd.mm.yyyy",
      startDate: "0d",
      todayBtn: true,
      todayHighlight: true,
      daysOfWeekDisabled: [0, 6],
      datesDisabled: data.datesForDisable,
      closeOnDateSelect: true,
      autoclose: true,
    })
    .on("changeDate", function (e) {
      // Enable masters on date change $("#datepicker").val(e.format());
    });
});
