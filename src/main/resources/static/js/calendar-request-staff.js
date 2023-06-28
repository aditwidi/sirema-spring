$(document).ready(function() {
    $('#calendar').fullCalendar({
        events: function(start, end, timezone, callback) {
            $.ajax({
                url: '/staff/calendar-data',
                type: 'GET',
                success: function(data) {
                    console.log("Raw Data:", data);
                    var events = data.map(function(event) {
                        return {
                            title: event.title,
                            start: event.date,
                            color: event.color
                        };
                    });
                    console.log("Converted events:", events);
                    callback(events);
                },
                error: function(error) {
                    console.log('Error fetching events', error);
                }
            });
        }
    });
});
