import { DashboardLayout } from "@/components/layouts/dashboard-layout";
import { CreateTicketForm } from "@/features/tickets/components/create-ticket-form";
import { Card, Stack, Title } from "@mantine/core";
import { FC } from "react";

export const CreateTicketPage: FC = () => {
  const handleCreateTicketSuccess = () => {};

  return (
    <DashboardLayout title="New Ticket">
      <Stack>
        <Title>New Ticket</Title>
        <Card p={30} withBorder>
          <div style={{ maxWidth: "40rem" }}>
            <CreateTicketForm onSuccess={handleCreateTicketSuccess} />
          </div>
        </Card>
      </Stack>
    </DashboardLayout>
  );
};
