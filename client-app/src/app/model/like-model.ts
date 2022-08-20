export class Like {
    constructor(
        public user: String = '',
        public destination: String = '',
        public time: Date = new Date()
    ) {}
}