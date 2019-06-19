$(function() {
			KindEditor.options.filterMode = true;
			var editor = KindEditor.create('#editor_id');
			$("#form").keydown(function() {
						if (event.keycode == 13) {
							event.preventDefault();
							return false;
						}
					});
			$("#submitBtn").click(function() {
						$("#description").val(editor.html());
						$("#form").submit();
					});
		});