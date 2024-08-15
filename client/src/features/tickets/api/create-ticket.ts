import { api } from "@/lib/api-client";
import { MutationConfig } from "@/lib/react-query";
import { useMutation } from "@tanstack/react-query";
import { z } from "zod";
import { CreateTicketResponse } from "../models/create-ticket-response";

export const createTicketInputSchema = z.object({
  // TODO: adjust later
  title: z.string(),
  description: z.string(),
  assignees: z.array(z.string()),
  groups: z.array(z.string()),
  priority: z.string(),
  category: z.string(),
  tags: z.array(z.string()),
});

export type CreateTicketInput = z.infer<typeof createTicketInputSchema>;

export const createTicket = ({
  data,
}: {
  data: CreateTicketInput;
}): Promise<CreateTicketResponse> => {
  // adjust later
  return api.post("/tickets", data);
};

type UseCreateTicketOptions = {
  mutationConfig?: MutationConfig<typeof createTicket>;
};

export const useCreateTicket = ({
  mutationConfig,
}: UseCreateTicketOptions = {}) => {
  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    ...restConfig,
    mutationFn: createTicket,
  });
};
