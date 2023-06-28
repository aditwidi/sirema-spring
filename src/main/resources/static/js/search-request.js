$(document).ready(function() {
    $(".form-control").on("keyup", function() {
        var namaPengaju = $("input[placeholder='Search by Nama Pengaju ...']").val().toLowerCase();
        var bentukRequest = $("input[placeholder='Search by Bentuk Request ...']").val().toLowerCase();
        var judulRequest = $("input[placeholder='Search by Judul Request ...']").val().toLowerCase();
        $.ajax({
            url: "/searchRequests",
            data: {
                namaPengaju: namaPengaju,
                bentukRequest: bentukRequest,
                judulRequest: judulRequest
            },
            success: function(response) {
                // Clear the existing table
                $(".datatable tbody").empty();

                if (response.length > 0) {
                    // Add new rows to the table
                    response.forEach(function(request) {
                        var statusClass = request.status === 'Ditolak' ? 'badge bg-danger' : (request.status === 'Disetujui' ? 'badge bg-success' : 'badge bg-warning');

                        var actions = '';
                        if (request.status === 'Pending' || request.status === 'Ditolak') {
                            actions = '<div class="actions">' +
                                '<a href="/user/requests/edit-request/' + request.id + '" class="btn btn-sm bg-success-light me-2">' +
                                '<i class="feather-edit"></i>' +
                                '</a>' +
                                '<a href="/user/requests/delete/' + request.id + '" class="btn btn-sm bg-danger-light">' +
                                '<i class="feather-trash-2"></i>' +
                                '</a>' +
                                '</div>';
                        } else if (request.status === 'Disetujui') {
                            actions = '<div class="actions" style="justify-content: center">' +
                                '<span>' +
                                '<a class="btn btn-sm bg-success rounded-circle">' +
                                '<i class="feather-check" style="color: white;"></i>' +
                                '</a>' +
                                '</span>' +
                                '</div>';
                        }

                        var row = '<tr>' +
                            '<td class="text-center">' + request.namaPengaju + '</td>' +
                            '<td class="text-center">' + request.bentukRequest + '</td>' +
                            '<td class="text-center">' + request.judulRequest + '</td>' +
                            '<td class="text-center">' + request.deadline + '</td>' +
                            '<td class="text-center"><span class="' + statusClass + '">' + request.status + '</span></td>' +
                            '<td class="text-end">' + actions + '</td>' +
                            '</tr>';
                        $(".datatable tbody").append(row);
                    });
                } else {
                    // Display 'No Result Found'
                    var row = '<tr>' +
                        '<td colspan="6" class="text-center">No Result Found</td>' +
                        '</tr>';
                    $(".datatable tbody").append(row);
                }
            },
            error: function() {
                // Handle error case
                var row = '<tr>' +
                    '<td colspan="6" class="text-center">Error fetching data</td>' +
                    '</tr>';
                $(".datatable tbody").append(row);
            }
        });
    });
});
