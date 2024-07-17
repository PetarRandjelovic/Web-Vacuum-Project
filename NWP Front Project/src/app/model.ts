export interface User{
    userId: number
    username: string,
    lastName: string,
    email: string,
    password: string,
   createuser:any,
    updateuser:any,
    readuser:any,
    deleteuser:any,
   
}
export interface LoginRequest{
    email: string,
    password: string
}

export interface Vacuum{
    id: number,
    name: string,
    status: string,
    active: boolean,
    date: string,
    user: User, 
    exists: boolean
}

export interface ErrorMessage{
    id: number,
    vacuumId: number,
    userId: number,
    date: string,
    errorType: string,
    errorMsg: string
}
