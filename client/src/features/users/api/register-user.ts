import { api } from "@/lib/api-client";
import { MutationConfig } from "@/lib/react-query";
import { MessageResponse } from "@/types/api";
import { useMutation } from "@tanstack/react-query";
import { z } from "zod";
import { RegisterUserResponseModel } from "../dtos/register-user-response";

export const registerUserInputSchema = z.object({
  username: z.string().min(3).max(20),
  email: z.string().email(),
  firstName: z.string().min(2),
  lastName: z.string().min(2),
  password: z.string().min(6),
});

export type RegisterUserInput = z.infer<typeof registerUserInputSchema>;

export const registerUser = ({
  data,
}: {
  data: RegisterUserInput;
}): Promise<RegisterUserResponseModel> => {
  // adjust later
  return api.post("/register", data);
};

type UseRegisterUserOptions = {
  mutationConfig?: MutationConfig<typeof registerUser>;
};

export const useRegisterUser = ({
  mutationConfig,
}: UseRegisterUserOptions = {}) => {
  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    ...restConfig,
    mutationFn: registerUser,
  });
};
