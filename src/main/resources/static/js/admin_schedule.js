document.addEventListener("DOMContentLoaded", function() {
    const editScheduleButton = document.getElementById("edit-schedule-button");
    const usernameSelect = document.getElementById("username");
    const weekIdSelect = document.getElementById("weekId");

    editScheduleButton.addEventListener("click", function() {
        const selectedUsername = usernameSelect.value;
        const selectedWeekId = weekIdSelect.value;

        if (!selectedUsername || !selectedWeekId) {
            alert("Please select a user and a week.");
            return;
        }

        // Redirect to the edit_schedule.html page with user and week parameters
        window.location.href = `/edit_schedule.html?username=${selectedUsername}&weekId=${selectedWeekId}`;
    });
});
