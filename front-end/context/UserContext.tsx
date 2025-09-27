'use client'

import { User } from "@/types/user/User";
import { useRouter } from "next/navigation";
import React, { createContext, useContext } from "react";

interface UserProviderProps {
    children: React.ReactNode;
}

interface UserContextType {
    token: string;
    user: User | null;
    changeToken: (newToken: string) => void;
    changeUser: (user: User) => void;
    handleUnauthorizedUser: () => void;
    
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export default function UserProvider({children}: UserProviderProps) {
    const [token, setToken] = React.useState<string>("");
    const [user, setUser] = React.useState<User | null>(null);
    const router = useRouter()

    function changeToken(newToken: string) {
        setToken(newToken)
    }

    function handleUnauthorizedUser() {
        router.push("/");
    }

    function changeUser(user: User) {
        setUser(user)
    }

    return (
        <UserContext.Provider value={{token, changeToken, handleUnauthorizedUser, user, changeUser}}>
            {children}
        </UserContext.Provider>
    )
}

export function useUser() {
    const context = useContext(UserContext);
    if (!context) throw new Error("useUser deve ser usado dentro de CartProvider");
    return context;
}