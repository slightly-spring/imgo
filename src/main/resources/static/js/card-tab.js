$(function(){
  $('.tabcontent > ul').hide();
  $('.tabnav div a').click(function () {
    $('.tabcontent > ul').hide().filter(this.hash).fadeIn();
    $('.tabnav div').removeClass('selected');
    $(this).parent().addClass('selected');
    return false;
  }).filter(':eq(0)').click();
});