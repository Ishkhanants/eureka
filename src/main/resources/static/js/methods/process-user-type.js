processUserType = str => {
    let locale = $('#locale').val();

    switch (str){
        case 'SECURITY_ADMINS':
            switch (locale){
                case 'hy':
                    return "Անվտանգության Ադմիններ";
                case 'en':
                    return 'Security Admins';
                case 'ru':
                    return "Админы Безопасности";
            }
            break;
        case 'ADMINS_MANAGEMENT':
            switch (locale){
                case 'hy':
                    return "Ադմիններ (Կառավարում)";
                case 'en':
                    return 'Admins (Management)';
                case 'ru':
                    return "Админы (Управление)";
            }
            break;
        case 'ADMINS_DEVELOPMENT':
            switch (locale){
                case 'hy':
                    return "Ադմիններ (Ծրագրավորում)";
                case 'en':
                    return 'Admins (Development)';
                case 'ru':
                    return "Админы (Разработка)";
            }
            break;
        case 'TESTERS':
            switch (locale){
                case 'hy':
                    return "Թեստավորողներ";
                case 'en':
                    return 'Testers';
                case 'ru':
                    return "Тестировщики";
            }
            break;
        case 'DEVELOPERS':
            switch (locale){
                case 'hy':
                    return "Ծրագրավորողներ";
                case 'en':
                    return 'Developers';
                case 'ru':
                    return "Разработчики";
            }
            break;
        case 'READ_ONLY_ACCESS':
            switch (locale){
                case 'hy':
                    return "Միայն Կարդալու Հասանելիություն";
                case 'en':
                    return 'Read Only Access';
                case 'ru':
                    return "Доступ Только Чтения";
            }
            break;
    }
}