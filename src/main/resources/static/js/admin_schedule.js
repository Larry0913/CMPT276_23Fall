document.addEventListener("DOMContentLoaded", function() {
    const editScheduleButton = document.getElementById("edit-schedule-button");
    const usernameSelect = document.getElementById("username");
    const weekNameSelect = document.getElementById("weekName");

    editScheduleButton.addEventListener("click", function() {
        const selectedUsername = usernameSelect.value;
        const selectedWeekName = weekNameSelect.value;

        if (!selectedUsername || !selectedWeekName) {
            alert("Please select a user and a week.");
            return;
        }

        // Redirect to the edit_schedule.html page with user and week parameters
        window.location.href = `/editSchedule.html?username=${selectedUsername}&weekName=${selectedWeekName}`;
    });
});
