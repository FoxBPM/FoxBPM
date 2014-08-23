$('#tabs a').click(function (e) {
  e.preventDefault()
  $(this).tab('show')
});

$('#tabs a[href="#activity"]').tab('show')