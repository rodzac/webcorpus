

function replace(tag)
{
	var selectedText = window.getSelection ? window.getSelection().toString() : document.selection.createRange().text;
	if(tag == null || tag == '')
		document.execCommand('insertHTML', false, selectedText);
	else
		document.execCommand('insertHTML', false, '<' + tag + '>' + selectedText + '</' + tag + '>');
}

function next()
{
	request('/document/next', 'GET', null, render);
}

function render(resp)
{
	$('#id').val(resp.document.id);
	$('#text').html(resp.document.text.replace('\n', '<br /><br />', 'g'));
	var tags = '';
	var styles = '<style type="text/css">';
	for(i = 0; i < resp.tags.length; i++)
	{
		tags += '<li><a href="#' + resp.tags[i].name + '"><' + resp.tags[i].name + '>' + resp.tags[i].label + '</' + resp.tags[i].name+ '></a></li>';
		styles += resp.tags[i].name + '{color: ' + resp.tags[i].color + ';} ';
	}
	tags += '<li><a href="#" class="text">Remover</a></li>';
	styles += '</style>';
	$('#styles').html(styles);
	$('#tags').html(tags);
}

function save()
{
	request('/document/' + $('#id').val(), 'POST', $('#text').html().replace('<br><br>', '\n', 'g'), alert($('#message-success').val()))
}

function archive()
{
	$('#mask').show();
	$('#archive').show();
}

function closeArchive()
{
	$('#mask').hide();
	$('#archive').hide();
	$('#list').html('');
}

function request(url, method, data, callback)
{
	$.ajax(
	{
		url: url,
		type:  method,
		data: data,
		success: callback
	});				
}

$(document).ready(function() { $("#text").contextMenu({menu: 'tags'}, function(action, el, pos) { replace(action); }); });

$( next() );