$(document).ready(async function () {
        // await $.i18n().load({
        //     "en": "/i18n/en.json",
        //     "hy": "/i18n/hy.json",
        //     "ru": "/i18n/ru.json",
        // });

        // Select/Deselect checkboxes
        let selectAll = $("#selectAll");
        let deleteAllSelected = $("#deleteAllSelected");
        let checkbox = $('table tbody input[type="checkbox"]');

        $(":checkbox").on('click', function () {
            let checkedCheckboxCount = $('table tbody input[type="checkbox"]:checked').length;
            checkedCheckboxCount > 0 ? deleteAllSelected.removeAttr("hidden") : deleteAllSelected.attr("hidden", true);
        })

        selectAll.click(function () {
            if (this.checked) {
                checkbox.each(function () {
                    this.checked = true;
                });
                deleteAllSelected.removeAttr("hidden");
            } else {
                checkbox.each(function () {
                    this.checked = false;
                });
                deleteAllSelected.attr("hidden", true);
            }
        });

        checkbox.click(function () {
            if (!this.checked) {
                $("#selectAll").prop("checked", false);
            }
        });

        $.extend($.fn.dataTableExt.oStdClasses, {
            'sPageNumber': 'paginate_number',
            'sPageNumbers': 'paginate_numbers'
        });

        $.fn.dataTableExt.oPagination.twoNumbers = {
            'oDefaults': {
                'iShowPages': 2
            },
            //for changing the page
            'fnClickHandler': function (e) {
                let fnCallbackDraw = e.data.fnCallbackDraw,
                    oSettings = e.data.oSettings,
                    sPage = e.data.sPage;

                if ($(this).is('[disabled]')) {
                    return false;
                }

                oSettings.oApi._fnPageChange(oSettings, sPage);
                fnCallbackDraw(oSettings);

                return true;
            },
            // fnInit is called once for each instance of pager
            // before loading only called once
            'fnInit': function (oSettings, nPager, fnCallbackDraw) {
                let oClasses = oSettings.oClasses,
                    oLang = oSettings.oLanguage.oPaginate,
                    that = this;

                let iShowPages = oSettings.oInit.iShowPages || this.oDefaults.iShowPages,
                    iShowPagesHalf = Math.floor(iShowPages / 2);

                $.extend(oSettings, {
                    _iShowPages: iShowPages,
                    _iShowPagesHalf: iShowPagesHalf,
                });

                let oFirst = $('<a class="' + oClasses.sPageButton + ' ' + oClasses.sPageFirst + '" style="cursor: pointer">' + oLang.sFirst + '</a>'),
                    oPrevious = $('<a class="' + oClasses.sPageButton + ' ' + oClasses.sPagePrevious + '" style="cursor: pointer">' + oLang.sPrevious + '</a>'),
                    oNumbers = $('<span class="' + oClasses.sPageNumbers + '"></span>'),
                    oNext = $('<a class="' + oClasses.sPageButton + ' ' + oClasses.sPageNext + '" style="cursor: pointer">' + oLang.sNext + '</a>'),
                    oLast = $('<a class="' + oClasses.sPageButton + ' ' + oClasses.sPageLast + '">' + oLang.sLast + '</a>');

                oFirst.click({
                    'fnCallbackDraw': fnCallbackDraw,
                    'oSettings': oSettings,
                    'sPage': 'first'
                }, that.fnClickHandler);
                oPrevious.click({
                    'fnCallbackDraw': fnCallbackDraw,
                    'oSettings': oSettings,
                    'sPage': 'previous'
                }, that.fnClickHandler);
                oNext.click({
                    'fnCallbackDraw': fnCallbackDraw,
                    'oSettings': oSettings,
                    'sPage': 'next'
                }, that.fnClickHandler);
                oLast.click({
                    'fnCallbackDraw': fnCallbackDraw,
                    'oSettings': oSettings,
                    'sPage': 'last'
                }, that.fnClickHandler);

                // Draw
                $(nPager).append(oPrevious, oNumbers, oNext);
            },
            // fnUpdate is only called once while table is rendered
            'fnUpdate': function (oSettings, fnCallbackDraw) {
                let oClasses = oSettings.oClasses,
                    that = this;

                let tableWrapper = oSettings.nTableWrapper;

                // Update stateful properties
                this.fnUpdateState(oSettings);

                if (oSettings._iCurrentPage === 1) {
                    $('.' + oClasses.sPageFirst, tableWrapper).attr('disabled', true);
                    $('.' + oClasses.sPagePrevious, tableWrapper).attr('disabled', true);
                } else {
                    $('.' + oClasses.sPageFirst, tableWrapper).attr('disabled', false);
                    $('.' + oClasses.sPagePrevious, tableWrapper).attr('disabled', false);
                }

                if (oSettings._iTotalPages === 0 || oSettings._iCurrentPage === oSettings._iTotalPages) {
                    $('.' + oClasses.sPageNext, tableWrapper).attr('disabled', true);
                    $('.' + oClasses.sPageLast, tableWrapper).attr('disabled', true);
                    $('.' + oClasses.sPageFirst, tableWrapper).attr('disabled', false);
                    $('.' + oClasses.sPagePrevious, tableWrapper).attr('disabled', false);
                } else {
                    $('.' + oClasses.sPageNext, tableWrapper).attr('disabled', false);
                    $('.' + oClasses.sPageLast, tableWrapper).attr('disabled', false);
                    $('.' + oClasses.sPageFirst, tableWrapper).attr('disabled', false);
                    $('.' + oClasses.sPagePrevious, tableWrapper).attr('disabled', false);
                }

                let i, oNumber, oNumbers = $('.' + oClasses.sPageNumbers, tableWrapper);

                // Erase
                oNumbers.html('');

                for (i = oSettings._iFirstPage; i <= oSettings._iLastPage; i++) {
                    oNumber = $('<button class="' + oClasses.sPageButton + ' ' + oClasses.sPageNumber + '">' + oSettings.fnFormatNumber(i) + '</button>');

                    if (oSettings._iCurrentPage === i) {
                        oNumber.attr('active', true).attr('disabled', true);
                        oNumber.addClass("activePage");
                    } else {
                        oNumber.click({
                            'fnCallbackDraw': fnCallbackDraw,
                            'oSettings': oSettings,
                            'sPage': i - 1
                        }, that.fnClickHandler);
                    }

                    // Draw
                    oNumbers.append(oNumber);
                }
            },
            // fnUpdateState used to be part of fnUpdate
            // The reason for moving is so we can access current state info before fnUpdate is called
            'fnUpdateState': function (oSettings) {
                let iCurrentPage = Math.ceil((oSettings._iDisplayStart + 1) / oSettings._iDisplayLength),
                    iTotalPages = Math.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength),
                    iFirstPage = iCurrentPage,
                    iLastPage = iCurrentPage + oSettings._iShowPagesHalf;

                if (iTotalPages < oSettings._iShowPages) {
                    iFirstPage = 1;
                    iLastPage = iTotalPages;
                } else if (iFirstPage < 1) {
                    iFirstPage = 1;
                    iLastPage = oSettings._iShowPages;
                } else if (iLastPage > iTotalPages) {
                    iFirstPage = (iTotalPages - oSettings._iShowPages) + 1;
                    iLastPage = iTotalPages;
                }

                $.extend(oSettings, {
                    _iCurrentPage: iCurrentPage,
                    _iTotalPages: iTotalPages,
                    _iFirstPage: iFirstPage,
                    _iLastPage: iLastPage
                });
            }
        };

        $.fn.dataTable.ext.search.push(
            function (settings, data, dataIndex) {
                let search = $("#myTable_filter input").val();
                let title = data[1];

                return data[0].toLowerCase().startsWith(search.toLocaleLowerCase())
                    || data[1].toLowerCase().startsWith(search.toLocaleLowerCase())
                    || data[2].toLowerCase().startsWith(search.toLocaleLowerCase())
                    || data[3].toLowerCase().startsWith(search.toLocaleLowerCase());
            }
        );

        let cls1 = document.getElementsByClassName('dataTables_filter');

        for (let i = 0; i < cls1.length; i++) {
            cls1[i].removeAttribute('id');
        }

        $("#myTable_filter input")
            .unbind()
            .bind("input", function (e) {
                if (this.value.length >= 0 || e.keyCode == 13) {
                    dtable.search(this.value, false, true).draw();
                }
                if (this.value == "") {
                    dtable.search("", true, false).draw();
                }
                return;
            });

        addErrorIcon();
    }
)