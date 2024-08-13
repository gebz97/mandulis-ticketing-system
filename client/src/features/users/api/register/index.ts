import { z } from "zod";

export const registerFormSchema = z.object({
    username:z.string().min(3).max(20),
    email: z.string().email(),
    firstName:z.string().min(2),
    lastName:z.string().min(2),
    password: z.string().min(6),
  });
  
  export type RegisterFormSchema = z.infer<typeof registerFormSchema>;