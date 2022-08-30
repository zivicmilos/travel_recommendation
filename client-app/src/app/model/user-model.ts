export class User {
    constructor(
        public username: string = '',
        public password: string = '',
        public loginBlocked: Date = new Date(),
        public reservationBlocked: Date = new Date(),
        public userRank: string = ''
    ) { }
}