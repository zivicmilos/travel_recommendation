export class User {
    constructor(
        public name: string = '',
        public lastname: string = '',
        public username: string = '',
        public password: string = '',
        public loginBlocked: Date = new Date(),
        public reservationBlocked: Date = new Date(),
        public userRank: string = '',
        public role: string = ''
    ) { }
}