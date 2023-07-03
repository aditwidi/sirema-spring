async function exportTable(buttonElement) {
    const userId = buttonElement.getAttribute('data-user-id');
    console.log('User ID:', userId);

    // Construct the URL
    const url = `/api/requests/user?userId=${userId}`;
    console.log('Constructed URL:', url);

    // Fetch data from back-end
    const response = await fetch(url);
    console.log('Response Status:', response.status);

    // Check if the response is OK
    if (!response.ok) {
        console.error('Network response was not ok');
        return;
    }

    const requestData = await response.json();
    console.log('Response Data:', requestData);

    // Prepare headers
    const wsData = [
        ['Nama Pengaju', 'Asal Pengaju', 'Nomor Handphone', 'Judul Request', 'Bentuk Request', 'Deadline', 'Status', 'Created At']
    ];

    // Add data to worksheet data
    requestData.forEach(request => {
        wsData.push([
            request.namaPengaju,
            request.asalPengaju,
            request.nomorHandphone,
            request.judulRequest,
            request.bentukRequest,
            request.deadline,
            request.status,
            request.createdAt
        ]);
    });

    // Create a workbook and worksheet
    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.aoa_to_sheet(wsData);
    XLSX.utils.book_append_sheet(wb, ws, 'Requests');

    // Export the workbook as an Excel file
    const filename = 'requests.xlsx';
    XLSX.writeFile(wb, filename);
}
