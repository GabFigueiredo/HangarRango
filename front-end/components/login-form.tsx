'use client'

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import z from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import { useMutation } from "@tanstack/react-query"
import { logIn } from "@/service/user/login"
import { LogInResponse, LogInUser } from "@/types/user/User"
import { useRouter } from "next/navigation"
import { toast } from "sonner"
import { useUser } from "@/context/UserContext"

const formSchema = z.object({
  email: z.email({message: "O email não pode estar vazio"}),
  senha: z.string({message: "A senha não pode estar vazia"})
})

type formSchemaType = z.infer<typeof formSchema>;

export function LoginForm({
  className,
  ...props
}: React.ComponentProps<"form">) 
{
  const { changeToken, changeUser } = useUser()
  const router = useRouter();

  const { mutate, isPending } = useMutation<LogInResponse, Error, LogInUser>({
    mutationFn: logIn,
    onSuccess: (response) => saveUserToken(response),
    onError: () => toast.error("Email ou senha incorretos.")
  })

  const { register, handleSubmit } = useForm<formSchemaType>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      email: "",
      senha: ""
    }
  })

  function onSubmit(data: formSchemaType) {
    mutate(data)

  }

  function saveUserToken(LogInResponse: LogInResponse) {
    // Salva no localStorage
    localStorage.setItem("USER_TOKEN", LogInResponse.token);

    // Salva no contexto
    changeToken(LogInResponse.token);

    console.log()
    changeUser(LogInResponse.usuario)
    router.push("/admin")
  }
  
  return (
    <form onSubmit={handleSubmit(onSubmit)} className={cn("flex flex-col gap-6", className)} {...props}>
      <div className="flex flex-col items-center gap-2 text-center">
        <h1 className="text-2xl font-bold">Entre na sua conta</h1>
        <p className="text-muted-foreground text-sm text-balance">
          Digite seu email abaixo para entrar na sua conta
        </p>
      </div>
      <div className="grid gap-6">
        <div className="grid gap-3">
          <Label htmlFor="email">Email</Label>
          <Input id="email" {...register("email")} type="email" placeholder="m@example.com" required />
        </div>
        <div className="grid gap-3">
          <div className="flex items-center">
            <Label htmlFor="password">Senha</Label>
          </div>
          <Input id="password" {...register("senha")} type="password" required />
        </div>
        <Button disabled={isPending} type="submit" className={`w-full ${isPending ? "cursor-not-allowed" : ""}`}>
          {isPending ? "Entrando..." : "Entrar"}
        </Button>
      </div>
    </form>
  )
}
