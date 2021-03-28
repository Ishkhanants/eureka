processUserType = str => {
    switch (str){
        case 'SECURITY_ADMINS':
            return 'Security Admins';
        case 'ADMINS_MANAGEMENT':
            return 'Admins (Management)';
        case 'ADMINS_DEVELOPMENT':
            return 'Admins (Development)';
        case 'TESTERS':
            return 'Testers';
        case 'DEVELOPERS':
            return 'Developers';
        case 'READ_ONLY_ACCESS':
            return 'Read Only Access';
    }
}