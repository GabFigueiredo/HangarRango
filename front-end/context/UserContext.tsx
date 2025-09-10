'use client'

import { routerServerGlobal } from "next/dist/server/lib/router-utils/router-server-context";
import { useRouter } from "next/navigation";
import React, { createContext, useContext } from "react";

interface UserProviderProps {
    children: React.ReactNode;
}

interface UserContextType {
    token: string;
    changeToken: (newToken: string) => void;
    handleUnauthorizedUser: () => void;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export default function UserProvider({children}: UserProviderProps) {
    const [token, setToken] = React.useState("");
    const router = useRouter()

    function changeToken(newToken: string) {
        setToken(newToken)
    }

    function handleUnauthorizedUser() {
        router.push("/");
    }

    return (
        <UserContext.Provider value={{token, changeToken, handleUnauthorizedUser}}>
            {children}
        </UserContext.Provider>
    )
}

export function useUser() {
    const context = useContext(UserContext);
    if (!context) throw new Error("useUser deve ser usado dentro de CartProvider");
    return context;
}