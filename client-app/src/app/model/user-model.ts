export class User {
    constructor(
        public name: string = '',
        public lastname: string = '',
        public username: string = '',
        public password: string = '',
        public email: string = '',
        public dateOfBirth: Date = new Date(),
        public userRank: string = '',
        public role: string = '',
        public averageSpent: number = 0,
        public sumSpent: number = 0
    ) { }
}