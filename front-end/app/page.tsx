"use client";

import { Button } from "@/components/ui/button";
import { Card, CardFooter, CardHeader } from "@/components/ui/card";
import Link from "next/link";
import { useRouter } from "next/navigation";


export default function HomePage() {
  const navigator = useRouter()

  function redirectToMainPage(): void {
    navigator.push("/cantina")
  }

  return <main className="flex w-full h-screen">
    <Card className="h-fit mx-auto my-auto">
      <CardHeader className="gap-5">
        <h1 className="text-2xl font-bold text-center">Bem vindo ao Hangar Rango!</h1>
        <Button className="hover:cursor-pointer" onClick={() => redirectToMainPage()}>Começar</Button>
      </CardHeader>
      <div>
        <CardFooter className="flex flex-col ">
          <p>Trabalha na cantina?</p>
          <Link href={"/login"} className="text-primary italic hover:underline">Clique aqui para fazer o login</Link>
        </CardFooter>
      </div>
    </Card>
  </main>
}
