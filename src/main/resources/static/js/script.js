const week = ["日", "月", "火", "水", "木", "金", "土"];
const today = new Date();
var showDate = new Date(today.getFullYear(), today.getMonth(), 1);

window.onload = function () {
    showProcess(today);
};

function prev() {
    showDate.setMonth(showDate.getMonth() - 1);
    showProcess(showDate);
}

function next() {
    showDate.setMonth(showDate.getMonth() + 1);
    showProcess(showDate);
}

function showProcess(date) {
    var year = date.getFullYear();
    var month = date.getMonth();
    document.querySelector('#header').innerHTML = year + "年 " + (month + 1) + "月";
    var calendar = createProcess(year, month);
    document.querySelector('#calendar').innerHTML = calendar;
}

function createProcess(year, month) {
    var calendar = "<table><tr class='dayOfWeek'>";
    for (var i = 0; i < week.length; i++) {
        calendar += "<th>" + week[i] + "</th>";
    }
    calendar += "</tr>";

    var count = 0;
    var startDayOfWeek = new Date(year, month, 1).getDay();
    var endDate = new Date(year, month + 1, 0).getDate();
    var lastMonthEndDate = new Date(year, month, 0).getDate();
    var row = Math.ceil((startDayOfWeek + endDate) / week.length);

    for (var i = 0; i < row; i++) {
        calendar += "<tr>";
        for (var j = 0; j < week.length; j++) {
            if (i == 0 && j < startDayOfWeek) {
                calendar += "<td class='disabled'><button onclick='goToClickedDate(" + (lastMonthEndDate - startDayOfWeek + j + 1) + ")'>" + (lastMonthEndDate - startDayOfWeek + j + 1) + "</button></td>";
            } else if (count >= endDate) {
                count++;
                calendar += "<td class='disabled'><button onclick='goToClickedDate(" + (count - endDate) + ")'>" + (count - endDate) + "</button></td>";
            } else {
                count++;
                if (year == today.getFullYear()
                    && month == (today.getMonth())
                    && count == today.getDate()) {
                    calendar += "<td class='today'><button onclick='goToClickedDate(" + count + ")'>" + count + "</button></td>";
                } else {
                    calendar += "<td><button onclick='goToClickedDate(" + count + ")'>" + count + "</button></td>";
                }
            }
        }
        calendar += "</tr>";
    }
    return calendar;
}

function goToClickedDate(date) {
        var clickedYear = showDate.getFullYear();
        var clickedMonth = showDate.getMonth() + 1; // 月は0から始まるため、1を加える

        // 新しいURLの構築
	var newURL = "/goToClickedDatePage?year=" + clickedYear + "&month=" + clickedMonth + "&day=" + date;

        // 新しいURLに遷移
        window.location.href = newURL;
}

